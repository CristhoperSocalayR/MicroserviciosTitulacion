package edu.pe.vallegrande.TypeKardex.service;

import edu.pe.vallegrande.TypeKardex.model.TypeKardex;
import edu.pe.vallegrande.TypeKardex.repository.TypeKardexRepository;
import edu.pe.vallegrande.TypeKardex.exception.TypeKardexNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class TypeKardexService {

    @Autowired
    private TypeKardexRepository repository;

    // Listar todos los registros
    public Flux<TypeKardex> listAll() {
        return repository.findAllByOrderByIdAsc();
    }

    // Listar solo los registros activos
    public Flux<TypeKardex> listActive() {
        return repository.findByStatus("A");
    }

    // Crear un nuevo TypeKardex
    public Mono<TypeKardex> create(TypeKardex typeKardex) {
        return repository.save(typeKardex);
    }

    // Editar un TypeKardex existente sin afectar su posición en la base de datos
    public Mono<TypeKardex> update(Long id, TypeKardex typeKardex) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new TypeKardexNotFoundException(id)))
                .flatMap(existingTypeKardex -> {
                    existingTypeKardex.setName(typeKardex.getName());
                    existingTypeKardex.setMaximumAmount(typeKardex.getMaximumAmount());
                    existingTypeKardex.setMinimumQuantity(typeKardex.getMinimumQuantity());
                    existingTypeKardex.setSupplierId(typeKardex.getSupplierId());
                    existingTypeKardex.setProductId(typeKardex.getProductId());
                    existingTypeKardex.setShedId(typeKardex.getShedId());
                    existingTypeKardex.setDescription(typeKardex.getDescription());
                    existingTypeKardex.setStatus(typeKardex.getStatus());

                    return repository.save(existingTypeKardex);
                });
    }

    // Eliminar lógicamente (cambia el estado a "I")
    public Mono<Void> deleteLogical(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new TypeKardexNotFoundException(id)))
                .flatMap(typeKardex -> {
                    typeKardex.setStatus("I");
                    return repository.save(typeKardex);
                })
                .then();
    }

    // Eliminar físicamente
    public Mono<Void> deletePhysical(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new TypeKardexNotFoundException(id)))
                .flatMap(typeKardex -> repository.deleteById(id));
    }

    // Restaurar (cambia el estado a "A")
    public Mono<Void> restore(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new TypeKardexNotFoundException(id)))
                .flatMap(typeKardex -> {
                    typeKardex.setStatus("A");
                    return repository.save(typeKardex);
                })
                .then();
    }
}