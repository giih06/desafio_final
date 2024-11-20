package com.desafio_final.desafio_final.controller.exceptions;

import com.desafio_final.desafio_final.service.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler {

    // ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandartError> resourceNotFound(ResourceNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandartError se = new StandartError(
                e.getMessage(),
                status.value());

        return ResponseEntity.status(status).body(se);
    }

    // DatabaseException
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandartError> databaseException(DatabaseException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandartError se = new StandartError(
                e.getMessage(),
                status.value());

        return ResponseEntity.status(status).body(se);

    }

    // AlreadyExsistsException
    @ExceptionHandler(AlreadyExsistsException.class)
    public ResponseEntity<StandartError> alreadyExists(AlreadyExsistsException e) {

        HttpStatus status = HttpStatus.CONFLICT;
        StandartError se = new StandartError(
                e.getMessage(),
                status.value());

        return ResponseEntity.status(status).body(se);
    }

    // RequierdException
    @ExceptionHandler(RequierdException.class)
    public ResponseEntity<MissingFieldError> requierdException(RequierdException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        MissingFieldError coe = new MissingFieldError(
                e.getMessage(),
                status.value(),
                e.getNomeDoCampo());

        return ResponseEntity.status(status).body(coe);
    }

    // InvalidFormatException
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<StandartError> invalidFormatException(InvalidFormatException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandartError se = new StandartError(
                e.getMessage(),
                status.value());

        return ResponseEntity.status(status).body(se);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError();
        err.setStatus(status.value());
        err.setMensagem("Validation exception");

        // Adicionando cada erro de campo na lista
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            err.addError(
                    fieldError.getDefaultMessage(), // Mensagem de erro
                    status.value(), // Status HTTP
                    fieldError.getField() // Nome do campo
            );
        }
        return ResponseEntity.status(status).body(err);
    }
}
