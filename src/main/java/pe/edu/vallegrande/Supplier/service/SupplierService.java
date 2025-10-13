package pe.edu.vallegrande.Supplier.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.Supplier.model.Supplier;
import pe.edu.vallegrande.Supplier.repository.SupplierRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository repository;

    // Lista todos
    public Flux<Supplier> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    // Buscar por ID
    public Mono<Supplier> findById(Long id) {
        return repository.findById(id);
    }

    // Crear nuevo proveedor
    public Mono<Supplier> create(Supplier supplier) {
        return repository.save(supplier);
    }

    // Editar proveedor existente
    public Mono<Supplier> update(Long id, Supplier supplier) {
        return repository.findById(id)
                .flatMap(existingSupplier -> {
                    existingSupplier.setRuc(supplier.getRuc());
                    existingSupplier.setCompany(supplier.getCompany());
                    existingSupplier.setName(supplier.getName());
                    existingSupplier.setLastname(supplier.getLastname());
                    existingSupplier.setEmail(supplier.getEmail());
                    existingSupplier.setCellphone(supplier.getCellphone());
                    existingSupplier.setNotes(supplier.getNotes());
                    existingSupplier.setStatus(supplier.getStatus());
                    existingSupplier.setTypeSupplierId(supplier.getTypeSupplierId());
                    return repository.save(existingSupplier);
                });
    }

    // Eliminar físicamente
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    // Eliminación lógica (cambiar el estado a "I")
    public Mono<Supplier> logicalDelete(Long id) {
        return repository.findById(id)
                .flatMap(supplier -> {
                    supplier.setStatus("I");
                    return repository.save(supplier);
                });
    }

    // Restaurar (cambiar el estado a "A")
    public Mono<Supplier> restore(Long id) {
        return repository.findById(id)
                .flatMap(supplier -> {
                    supplier.setStatus("A");
                    return repository.save(supplier);
                });
    }
}
