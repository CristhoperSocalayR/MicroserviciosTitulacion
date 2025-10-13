package pe.edu.vallegrande.Ubigeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    private static final List<String> STATIC_ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost:4200"
    );

    private static final Pattern GITPOD_REGEX = Pattern.compile(
            "^https://4200-[a-z0-9\\-]+\\.ws-[a-z0-9]+\\.gitpod\\.io$"
    );

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        // Permitir OPTIONS para CORS
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        
                        // Permitir acceso a documentación Swagger
                        .pathMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        
                        // ==================== TYPE SUPPLIERS ENDPOINTS ====================
                        // GET - Todos los usuarios pueden ver los tipos de proveedores
                        .pathMatchers(HttpMethod.GET, "/NPH/type-suppliers", "/NPH/type-suppliers/**").hasAnyRole("USER", "ADMIN")
                        
                        // POST - Solo administradores pueden crear tipos de proveedores
                        .pathMatchers(HttpMethod.POST, "/NPH/type-suppliers").hasRole("ADMIN")
                        
                        // PUT - Solo administradores pueden actualizar tipos de proveedores
                        .pathMatchers(HttpMethod.PUT, "/NPH/type-suppliers/**").hasRole("ADMIN")
                        
                        // DELETE - Solo administradores pueden eliminar tipos de proveedores
                        .pathMatchers(HttpMethod.DELETE, "/NPH/type-suppliers/**").hasRole("ADMIN")
                        
                        // ==================== SUPPLIERS ENDPOINTS ====================
                        // GET - Todos los usuarios pueden ver proveedores
                        .pathMatchers(HttpMethod.GET, "/NPH/suppliers", "/NPH/suppliers/**").hasAnyRole("USER", "ADMIN")
                        
                        // POST - Solo administradores pueden crear proveedores
                        .pathMatchers(HttpMethod.POST, "/NPH/suppliers").hasRole("ADMIN")
                        
                        // PUT - Solo administradores pueden actualizar proveedores
                        .pathMatchers(HttpMethod.PUT, "/NPH/suppliers/**").hasRole("ADMIN")
                        
                        // DELETE - Solo administradores pueden eliminar proveedores
                        .pathMatchers(HttpMethod.DELETE, "/NPH/suppliers/**").hasRole("ADMIN")
                        
                        // ==================== UBIGEO ENDPOINTS ====================
                        // GET - Todos los usuarios pueden consultar ubigeos (información geográfica)
                        .pathMatchers(HttpMethod.GET, "/NPH/ubigeo", "/NPH/ubigeo/**").hasAnyRole("USER", "ADMIN")
                        
                        // POST - Solo administradores pueden crear ubigeos
                        .pathMatchers(HttpMethod.POST, "/NPH/ubigeo").hasRole("ADMIN")
                        
                        // PUT - Solo administradores pueden actualizar ubigeos
                        .pathMatchers(HttpMethod.PUT, "/NPH/ubigeo/**").hasRole("ADMIN")
                        
                        // DELETE - Solo administradores pueden eliminar ubigeos
                        .pathMatchers(HttpMethod.DELETE, "/NPH/ubigeo/**").hasRole("ADMIN")
                        
                        // Cualquier otro endpoint requiere autenticación
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtDecoder(jwtDecoder())
                                .jwtAuthenticationConverter(this::convertJwt)
                        )
                )
                .cors(cors -> cors
                        .configurationSource(dynamicCorsConfigurationSource())
                )
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    private Mono<CustomAuthenticationToken> convertJwt(Jwt jwt) {
        String role = jwt.getClaimAsString("role");
        Collection<GrantedAuthority> authorities = role != null
                ? List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                : List.of();
        return Mono.just(new CustomAuthenticationToken(jwt, authorities));
    }

    /**
     * 🔄 Configuración dinámica de CORS para admitir localhost y Gitpod.
     */
    private CorsConfigurationSource dynamicCorsConfigurationSource() {
        return new UrlBasedCorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
                String origin = exchange.getRequest().getHeaders().getOrigin();
                if (isAllowedOrigin(origin)) {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(origin));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    return config;
                }
                return null; // CORS bloqueado si el origen no es válido
            }
        };
    }

    private boolean isAllowedOrigin(String origin) {
        if (origin == null) return false;
        return STATIC_ALLOWED_ORIGINS.contains(origin) || GITPOD_REGEX.matcher(origin).matches();
    }
}