package com.lodge_treasury.management.exception;

import com.lodge_treasury.management.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MasonAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleMasonAlreadyExistsException(MasonAlreadyExistsException ex
            , WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DegreeNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDegreeNotFoundException(DegreeNotFoundException ex,
                                                                          WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutstandingDebtException.class)
    public ResponseEntity<ErrorResponseDto> handleOutstandingDebt (OutstandingDebtException ex, WebRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
