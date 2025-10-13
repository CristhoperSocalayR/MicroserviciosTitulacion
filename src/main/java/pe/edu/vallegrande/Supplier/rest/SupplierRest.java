package pe.edu.vallegrande.Supplier.rest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.Supplier.model.Supplier;
import pe.edu.vallegrande.Supplier.service.SupplierService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/NPH/suppliers")
@RequiredArgsConstructor
public class SupplierRest {

    private final SupplierService service;

    @GetMapping
    public Flux<Supplier> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Supplier>> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Supplier> create(@RequestBody Supplier supplier) {
        return service.create(supplier);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Supplier>> update(@PathVariable Long id, @RequestBody Supplier supplier) {
        return service.update(id, supplier)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/{id}/logico")
    public Mono<ResponseEntity<Supplier>> logicalDelete(@PathVariable Long id) {
        return service.logicalDelete(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/restaurar")
    public Mono<ResponseEntity<Supplier>> restore(@PathVariable Long id) {
        return service.restore(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

