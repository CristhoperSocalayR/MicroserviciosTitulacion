package pe.edu.vallegrande.TypeSupplier.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.TypeSupplier.model.TypeSupplier;
import pe.edu.vallegrande.TypeSupplier.repository.TypeSupplierRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TypeSupplierService {

    private final TypeSupplierRepository repository;

    public Flux<TypeSupplier> getAll() {
        return repository.findAllByOrderByIdAsc(); // Changed to fetch all records without filtering by status
    }

    public Mono<TypeSupplier> getById(Long id) {
        return repository.findById(id);
    }

    public Mono<TypeSupplier> create(TypeSupplier typeSupplier) {
        return repository.save(typeSupplier);
    }

    public Mono<TypeSupplier> update(Long id, TypeSupplier updatedTypeSupplier) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setName(updatedTypeSupplier.getName());
                    existing.setAddress(updatedTypeSupplier.getAddress());
                    existing.setUbigeoId(updatedTypeSupplier.getUbigeoId());
                    return repository.save(existing);
                });
    }

    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    public Mono<TypeSupplier> softDelete(Long id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setStatus("I");
                    return repository.save(existing);
                });
    }

    public Mono<TypeSupplier> restore(Long id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setStatus("A");
                    return repository.save(existing);
                });
    }
}
