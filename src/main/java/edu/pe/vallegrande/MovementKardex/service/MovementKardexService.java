package edu.pe.vallegrande.MovementKardex.service;

import edu.pe.vallegrande.MovementKardex.model.MovementKardex;
import edu.pe.vallegrande.MovementKardex.repository.MovementKardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class MovementKardexService {

    private final MovementKardexRepository repository;

    @Autowired
    public MovementKardexService(MovementKardexRepository repository) {
        this.repository = repository;
    }

    // Listar todos los movimientos de Kardex
    public Flux<MovementKardex> listarTodos() {
        return repository.findAllByOrderByKardexIdAsc();
    }

    // Crear un nuevo movimiento de Kardex
    public Mono<MovementKardex> crear(MovementKardex movementKardex) {
        return repository.save(movementKardex);
    }

    // Editar un movimiento de Kardex existente
    public Mono<MovementKardex> editar(Long kardexId, MovementKardex movementKardex) {
        return repository.findById(kardexId)
                .flatMap(existingMovement -> {
                    existingMovement.setIssueDate(movementKardex.getIssueDate());
                    existingMovement.setConcept(movementKardex.getConcept());
                    existingMovement.setDocumentType(movementKardex.getDocumentType());
                    existingMovement.setDocumentNumber(movementKardex.getDocumentNumber());
                    existingMovement.setCantidadEntrada(movementKardex.getCantidadEntrada());
                    existingMovement.setCostoUnitarioEntrada(movementKardex.getCostoUnitarioEntrada());
                    existingMovement.setValorTotalEntrada(movementKardex.getValorTotalEntrada());
                    existingMovement.setCantidadSalida(movementKardex.getCantidadSalida());
                    existingMovement.setCostoUnitarioSalida(movementKardex.getCostoUnitarioSalida());
                    existingMovement.setValorTotalSalida(movementKardex.getValorTotalSalida());
                    existingMovement.setCantidadSaldo(movementKardex.getCantidadSaldo());
                    existingMovement.setCostoUnitarioSaldo(movementKardex.getCostoUnitarioSaldo());
                    existingMovement.setValorTotalSaldo(movementKardex.getValorTotalSaldo());
                    existingMovement.setObservation(movementKardex.getObservation());
                    existingMovement.setTypeKardexId(movementKardex.getTypeKardexId());
                    return repository.save(existingMovement);
                });
    }

    // Eliminar físicamente un movimiento de Kardex
    public Mono<Void> eliminar(Long kardexId) {
        return repository.deleteById(kardexId);
    }
}
