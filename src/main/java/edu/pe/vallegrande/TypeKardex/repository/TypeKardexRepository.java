package edu.pe.vallegrande.TypeKardex.repository;
import edu.pe.vallegrande.TypeKardex.model.TypeKardex;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TypeKardexRepository extends ReactiveCrudRepository<TypeKardex, Long> {
    Flux<TypeKardex> findByStatus(String status);
    Flux<TypeKardex> findAllByOrderByIdAsc();
}
