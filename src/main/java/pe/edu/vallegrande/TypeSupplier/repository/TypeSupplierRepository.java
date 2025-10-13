package pe.edu.vallegrande.TypeSupplier.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.TypeSupplier.model.TypeSupplier;
import reactor.core.publisher.Flux;

@Repository
public interface TypeSupplierRepository extends ReactiveCrudRepository<TypeSupplier, Long> {
    Flux<TypeSupplier>findAllByOrderByIdAsc();
}

