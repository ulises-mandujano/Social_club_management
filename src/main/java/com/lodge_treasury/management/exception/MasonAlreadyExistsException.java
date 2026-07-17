package com.lodge_treasury.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MasonAlreadyExistsException extends RuntimeException {

    public MasonAlreadyExistsException(String message) {super(message);}

}
