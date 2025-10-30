package edu.pe.vallegrande.TypeKardex.controller;

import edu.pe.vallegrande.TypeKardex.model.TypeKardex;
import edu.pe.vallegrande.TypeKardex.service.TypeKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/NPH/type-kardex")
public class ControlllerTypeKardex {

    @Autowired
    private TypeKardexService service;

    // Listar todos los registros
    @GetMapping("/list-all")
    public Flux<TypeKardex> listAll() {
        return service.listAll();
    }

    // Listar solo los registros activos
    @GetMapping("/list")
    public Flux<TypeKardex> listActive() {
        return service.listActive();
    }

    // Crear un nuevo TypeKardex
    @PostMapping("/create")
    public Mono<ResponseEntity<TypeKardex>> create(@RequestBody TypeKardex typeKardex) {
        return service.create(typeKardex)
                .map(savedTypeKardex -> ResponseEntity.status(HttpStatus.CREATED).body(savedTypeKardex));
    }

    // Editar un TypeKardex existente
    @PutMapping("/edit/{id}")
    public Mono<ResponseEntity<TypeKardex>> update(@PathVariable Long id, @RequestBody TypeKardex typeKardex) {
        return service.update(id, typeKardex)
                .map(updatedTypeKardex -> ResponseEntity.ok(updatedTypeKardex));
    }

    // Eliminar lógicamente (cambia el estado a "I")
    @PutMapping("/delete-logical/{id}")
    public Mono<ResponseEntity<Void>> deleteLogical(@PathVariable Long id) {
        return service.deleteLogical(id)
                .map(result -> ResponseEntity.ok().<Void>build());
    }

    // Eliminar físicamente
    @DeleteMapping("/delete-physical/{id}")
    public Mono<ResponseEntity<Void>> deletePhysical(@PathVariable Long id) {
        return service.deletePhysical(id)
                .map(result -> ResponseEntity.ok().<Void>build());
    }

    // Restaurar (cambia el estado a "A")
    @PutMapping("/restore/{id}")
    public Mono<ResponseEntity<Void>> restore(@PathVariable Long id) {
        return service.restore(id)
                .map(result -> ResponseEntity.ok().<Void>build());
    }
}