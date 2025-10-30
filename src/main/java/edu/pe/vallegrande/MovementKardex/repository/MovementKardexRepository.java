package edu.pe.vallegrande.MovementKardex.repository;

import edu.pe.vallegrande.MovementKardex.model.MovementKardex;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MovementKardexRepository extends ReactiveCrudRepository<MovementKardex, Long> {
    Flux<MovementKardex> findAllByOrderByKardexIdAsc();
}