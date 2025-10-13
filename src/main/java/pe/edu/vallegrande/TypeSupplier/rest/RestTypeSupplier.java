package pe.edu.vallegrande.TypeSupplier.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.TypeSupplier.model.TypeSupplier;
import pe.edu.vallegrande.TypeSupplier.service.TypeSupplierService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/NPH/type-suppliers")
@RequiredArgsConstructor
public class RestTypeSupplier {

    private final TypeSupplierService service;

    @GetMapping
    public Flux<TypeSupplier> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TypeSupplier>> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<TypeSupplier> create(@RequestBody TypeSupplier typeSupplier) {
        return service.create(typeSupplier);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TypeSupplier>> update(@PathVariable Long id, @RequestBody TypeSupplier typeSupplier) {
        return service.update(id, typeSupplier)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable Long id) {
        return service.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/logico/{id}")
    public Mono<ResponseEntity<TypeSupplier>> softDelete(@PathVariable Long id) {
        return service.softDelete(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/restaurar/{id}")
    public Mono<ResponseEntity<TypeSupplier>> restore(@PathVariable Long id) {
        return service.restore(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

