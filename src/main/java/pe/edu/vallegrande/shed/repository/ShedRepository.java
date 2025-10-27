package pe.edu.vallegrande.shed.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.shed.model.Shed;

@Repository
public interface ShedRepository extends ReactiveCrudRepository<Shed, Long> {

}