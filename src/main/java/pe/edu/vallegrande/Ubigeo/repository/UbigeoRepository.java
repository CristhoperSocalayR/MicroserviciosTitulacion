package pe.edu.vallegrande.Ubigeo.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import pe.edu.vallegrande.Ubigeo.model.Ubigeo;
import reactor.core.publisher.Flux;

public interface UbigeoRepository extends ReactiveCrudRepository<Ubigeo, Long> {
    Flux<Ubigeo> findAllByOrderByIdAsc();
}
