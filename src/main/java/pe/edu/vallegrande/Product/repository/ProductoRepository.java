package pe.edu.vallegrande.Product.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.Product.ProductoModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoModel, Long> {
    
    Mono<ProductoModel> findByIdAndStatus(Long id, String status);

    // Filtrar productos por estado ("A" para activos)
    Flux<ProductoModel> findByStatus(String status);
}