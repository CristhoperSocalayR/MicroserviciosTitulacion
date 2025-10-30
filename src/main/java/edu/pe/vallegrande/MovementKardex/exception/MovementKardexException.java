package edu.pe.vallegrande.MovementKardex.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepciones para el sistema MovementKardex
 */
@Getter
public class MovementKardexException extends RuntimeException {
    
    private final HttpStatus httpStatus;
    private final String errorCode;

    public MovementKardexException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public MovementKardexException(String message, Throwable cause, HttpStatus httpStatus, String errorCode) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    // Método estático para crear excepción de "no encontrado"
    public static MovementKardexException notFound(Long kardexId) {
        return new MovementKardexException(
            "Movimiento de Kardex con ID " + kardexId + " no encontrado",
            HttpStatus.NOT_FOUND,
            "NOT_FOUND"
        );
    }

    // Método estático para crear excepción de validación
    public static MovementKardexException validation(String message) {
        return new MovementKardexException(
            message,
            HttpStatus.BAD_REQUEST,
            "VALIDATION_ERROR"
        );
    }

    // Método estático para crear excepción de base de datos
    public static MovementKardexException database(String message, Throwable cause) {
        return new MovementKardexException(
            message,
            cause,
            HttpStatus.INTERNAL_SERVER_ERROR,
            "DATABASE_ERROR"
        );
    }

    // Método estático para crear excepción de cálculo
    public static MovementKardexException calculation(String message) {
        return new MovementKardexException(
            message,
            HttpStatus.UNPROCESSABLE_ENTITY,
            "CALCULATION_ERROR"
        );
    }

    // Método estático para crear excepción de conflicto
    public static MovementKardexException conflict(String message) {
        return new MovementKardexException(
            message,
            HttpStatus.CONFLICT,
            "CONFLICT_ERROR"
        );
    }
}