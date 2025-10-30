package edu.pe.vallegrande.TypeKardex.exception;

public class TypeKardexNotFoundException extends RuntimeException {
    public TypeKardexNotFoundException(String message) {
        super(message);
    }
    
    public TypeKardexNotFoundException(Long id) {
        super("TypeKardex with id " + id + " not found");
    }
}