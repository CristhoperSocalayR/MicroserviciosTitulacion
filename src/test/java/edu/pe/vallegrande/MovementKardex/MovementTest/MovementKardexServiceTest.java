package edu.pe.vallegrande.MovementKardex.service;

import edu.pe.vallegrande.MovementKardex.model.MovementKardex;
import edu.pe.vallegrande.MovementKardex.repository.MovementKardexRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas del Servicio MovementKardex")
class MovementKardexServiceTest {

    @Mock
    private MovementKardexRepository repository;

    @InjectMocks
    private MovementKardexService service;

    private MovementKardex movementKardex;
    private MovementKardex updatedMovementKardex;

    @BeforeEach
    @DisplayName("Configuración inicial antes de cada prueba")
    void setUp() {
        movementKardex = new MovementKardex();
        movementKardex.setKardexId(1L);  // Changed from 
        movementKardex.setIssueDate(LocalDate.now());
        movementKardex.setConcept("Compra de productos");
        movementKardex.setDocumentType("Factura");
        movementKardex.setDocumentNumber("F001-123");
        movementKardex.setCantidadEntrada(BigDecimal.valueOf(10));
        movementKardex.setCostoUnitarioEntrada(BigDecimal.valueOf(15.50));
        movementKardex.setValorTotalEntrada(BigDecimal.valueOf(155.00));
        movementKardex.setCantidadSalida(BigDecimal.ZERO);
        movementKardex.setCostoUnitarioSalida(BigDecimal.ZERO);
        movementKardex.setValorTotalSalida(BigDecimal.ZERO);
        movementKardex.setCantidadSaldo(BigDecimal.valueOf(10));
        movementKardex.setCostoUnitarioSaldo(BigDecimal.valueOf(15.50));
        movementKardex.setValorTotalSaldo(BigDecimal.valueOf(155.00));
        movementKardex.setObservation("Entrada inicial de inventario");
        movementKardex.setTypeKardexId(1);  // Changed from Long to Integer

        updatedMovementKardex = new MovementKardex();
        updatedMovementKardex.setKardexId(1L);  // Changed from int to Long
        updatedMovementKardex.setIssueDate(LocalDate.now().plusDays(1));
        updatedMovementKardex.setConcept("Venta de productos");
        updatedMovementKardex.setDocumentType("Boleta");
        updatedMovementKardex.setDocumentNumber("B001-456");
        updatedMovementKardex.setCantidadEntrada(BigDecimal.ZERO);
        updatedMovementKardex.setCostoUnitarioEntrada(BigDecimal.ZERO);
        updatedMovementKardex.setValorTotalEntrada(BigDecimal.ZERO);
        updatedMovementKardex.setCantidadSalida(BigDecimal.valueOf(5));
        updatedMovementKardex.setCostoUnitarioSalida(BigDecimal.valueOf(15.50));
        updatedMovementKardex.setValorTotalSalida(BigDecimal.valueOf(77.50));
        updatedMovementKardex.setCantidadSaldo(BigDecimal.valueOf(5));
        updatedMovementKardex.setCostoUnitarioSaldo(BigDecimal.valueOf(15.50));
        updatedMovementKardex.setValorTotalSaldo(BigDecimal.valueOf(77.50));
        updatedMovementKardex.setObservation("Venta a cliente");
        updatedMovementKardex.setTypeKardexId(2);  // Changed from long to Integer
    }

    @Test
    @DisplayName("Debe listar todos los movimientos de Kardex ordenados por ID")
    void debeListarTodosLosMovimientos() {
        // Given
        MovementKardex movement1 = new MovementKardex();
        movement1.setKardexId(1L);  // Changed from int to Long
        movement1.setConcept("Movimiento 1");
        
        MovementKardex movement2 = new MovementKardex();
        movement2.setKardexId(2L);  // Changed from int to Long
        movement2.setConcept("Movimiento 2");
        
        when(repository.findAllByOrderByKardexIdAsc())
            .thenReturn(Flux.just(movement1, movement2));

        // When
        Flux<MovementKardex> result = service.listarTodos();

        // Then
        StepVerifier.create(result)
            .expectNext(movement1)
            .expectNext(movement2)
            .verifyComplete();
        
        verify(repository, times(1)).findAllByOrderByKardexIdAsc();
    }

    @Test
    @DisplayName("Debe crear un nuevo movimiento de Kardex exitosamente")
    void debeCrearNuevoMovimientoExitosamente() {
        // Given
        when(repository.save(any(MovementKardex.class)))
            .thenReturn(Mono.just(movementKardex));

        // When
        Mono<MovementKardex> result = service.crear(movementKardex);

        // Then's
        StepVerifier.create(result)
            .expectNext(movementKardex)
            .verifyComplete();
        
        verify(repository, times(1)).save(movementKardex);
    }

    @Test
    @DisplayName("Debe editar un movimiento de Kardex existente correctamente")
    void debeEditarMovimientoExistente() {
        // Given
        when(repository.findById(1L))  // Changed from int to Long
            .thenReturn(Mono.just(movementKardex));
        when(repository.save(any(MovementKardex.class)))
            .thenReturn(Mono.just(movementKardex));

        // When
        Mono<MovementKardex> result = service.editar(1L, updatedMovementKardex);

        // Then
        StepVerifier.create(result)
            .expectNextMatches(movement -> 
                movement.getKardexId().equals(1L) &&  // Changed from int to Long
                movement.getConcept().equals("Venta de productos") &&
                movement.getDocumentType().equals("Boleta") &&
                movement.getDocumentNumber().equals("B001-456") &&
                movement.getCantidadSalida().equals(BigDecimal.valueOf(5)) &&
                movement.getTypeKardexId().equals(2)  // Changed from Long to Integer
            )
            .verifyComplete();
        
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(MovementKardex.class));
    }

    @Test
    @DisplayName("Debe retornar vacío cuando el movimiento a editar no existe")
    void debeRetornarVacioSiMovimientoNoExiste() {
        // Given
        when(repository.findById(anyLong()))
            .thenReturn(Mono.empty());

        // When
        Mono<MovementKardex> result = service.editar(999L, updatedMovementKardex);

        // Then
        StepVerifier.create(result)
            .verifyComplete();
        
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).save(any(MovementKardex.class));
    }

    @Test
    @DisplayName("Debe eliminar físicamente un movimiento de Kardex por ID")
    void debeEliminarMovimientoPorId() {
        // Given
        when(repository.deleteById(1L))
            .thenReturn(Mono.empty());

        // When
        Mono<Void> result = service.eliminar(1L);

        // Then
        StepVerifier.create(result)
            .verifyComplete();
        
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe manejar error al crear movimiento con datos nulos")
    void debeManejarErrorAlCrearConDatosNulos() {
        // Given
        when(repository.save(any(MovementKardex.class)))
            .thenReturn(Mono.error(new IllegalArgumentException("Datos inválidos")));

        // When
        Mono<MovementKardex> result = service.crear(new MovementKardex());

        // Then
        StepVerifier.create(result)
            .expectError(IllegalArgumentException.class)
            .verify();
        
        verify(repository, times(1)).save(any(MovementKardex.class));
    }

    @Test
    @DisplayName("Debe manejar error al eliminar movimiento inexistente")
    void debeManejarErrorAlEliminarMovimientoInexistente() {
        // Given
        when(repository.deleteById(anyLong()))
            .thenReturn(Mono.error(new RuntimeException("Movimiento no encontrado")));

        // When
        Mono<Void> result = service.eliminar(999L);

        // Then
        StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();
        
        verify(repository, times(1)).deleteById(999L);
    }

    @Test
    @DisplayName("Debe verificar que todos los campos se actualizan correctamente en edición")
    void debeVerificarActualizacionCompletaDecampos() {
        // Given
        MovementKardex originalMovement = new MovementKardex();
        originalMovement.setKardexId(1L);
        originalMovement.setIssueDate(LocalDate.now().minusDays(1));
        originalMovement.setConcept("Concepto original");
        originalMovement.setDocumentType("Tipo original");
        originalMovement.setDocumentNumber("DOC-001");
        originalMovement.setCantidadEntrada(BigDecimal.valueOf(100));
        originalMovement.setCostoUnitarioEntrada(BigDecimal.valueOf(10));
        originalMovement.setValorTotalEntrada(BigDecimal.valueOf(1000));
        originalMovement.setCantidadSalida(BigDecimal.ZERO);
        originalMovement.setCostoUnitarioSalida(BigDecimal.ZERO);
        originalMovement.setValorTotalSalida(BigDecimal.ZERO);
        originalMovement.setCantidadSaldo(BigDecimal.valueOf(100));
        originalMovement.setCostoUnitarioSaldo(BigDecimal.valueOf(10));
        originalMovement.setValorTotalSaldo(BigDecimal.valueOf(1000));
        originalMovement.setObservation("Observación original");
        originalMovement.setTypeKardexId(1);  // Changed from Long to Integer

        when(repository.findById(1L))
            .thenReturn(Mono.just(originalMovement));
        when(repository.save(any(MovementKardex.class)))
            .thenReturn(Mono.just(originalMovement));

        // When
        Mono<MovementKardex> result = service.editar(1L, updatedMovementKardex);

        // Then
        StepVerifier.create(result)
            .expectNextMatches(movement -> {
                // Verificar que todos los campos fueron actualizados
                return movement.getIssueDate().equals(updatedMovementKardex.getIssueDate()) &&
                       movement.getConcept().equals(updatedMovementKardex.getConcept()) &&
                       movement.getDocumentType().equals(updatedMovementKardex.getDocumentType()) &&
                       movement.getDocumentNumber().equals(updatedMovementKardex.getDocumentNumber()) &&
                       movement.getCantidadEntrada().equals(updatedMovementKardex.getCantidadEntrada()) &&
                       movement.getCostoUnitarioEntrada().equals(updatedMovementKardex.getCostoUnitarioEntrada()) &&
                       movement.getValorTotalEntrada().equals(updatedMovementKardex.getValorTotalEntrada()) &&
                       movement.getCantidadSalida().equals(updatedMovementKardex.getCantidadSalida()) &&
                       movement.getCostoUnitarioSalida().equals(updatedMovementKardex.getCostoUnitarioSalida()) &&
                       movement.getValorTotalSalida().equals(updatedMovementKardex.getValorTotalSalida()) &&
                       movement.getCantidadSaldo().equals(updatedMovementKardex.getCantidadSaldo()) &&
                       movement.getCostoUnitarioSaldo().equals(updatedMovementKardex.getCostoUnitarioSaldo()) &&
                       movement.getValorTotalSaldo().equals(updatedMovementKardex.getValorTotalSaldo()) &&
                       movement.getObservation().equals(updatedMovementKardex.getObservation()) &&
                       movement.getTypeKardexId().equals(updatedMovementKardex.getTypeKardexId());
            })
            .verifyComplete();
        
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(MovementKardex.class));
    }
}