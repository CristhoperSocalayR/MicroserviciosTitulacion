package pe.edu.vallegrande.Supplier.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.Supplier.model.Supplier;
import reactor.core.publisher.Flux;

@Repository
public interface SupplierRepository extends ReactiveCrudRepository<Supplier, Long> {
    Flux<Supplier> findAllByOrderByIdAsc();
}
