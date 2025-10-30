package edu.pe.vallegrande.TypeKardex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class SwaggerConfig implements WebFluxConfigurer {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .url("https://8085-vallegrande-mstypekarde-o3a8rupypef.ws-us118.gitpod.io/")  // Cambia aquí la URL para localhost
                        .description("Servidor de desarrollo"))
                .info(new Info()
                        .title("API REST DE NPH")
                        .description("Especificación de REST API services")
                        .license(new License()
                                .name("Valle Grande")
                                .url("https://vallegrande.edu.pe"))
                        .version("1.0.0")
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
