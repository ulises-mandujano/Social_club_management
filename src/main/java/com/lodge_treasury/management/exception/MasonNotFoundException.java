package com.lodge_treasury.management.exception;

public class MasonNotFoundException extends RuntimeException {
    public MasonNotFoundException(Integer id) {
        super("Mason not found with id: " + id);
    }
}
