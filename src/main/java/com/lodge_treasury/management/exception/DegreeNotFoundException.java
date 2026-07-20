package com.lodge_treasury.management.exception;

public class DegreeNotFoundException extends RuntimeException {
    public DegreeNotFoundException(String degreeCode) {
        super("Grado no encontrado con código: " + degreeCode);
    }

    public DegreeNotFoundException(String degreeCode, Throwable cause) {
        super("Grado no encontrado con código: " + degreeCode, cause);
    }
}
