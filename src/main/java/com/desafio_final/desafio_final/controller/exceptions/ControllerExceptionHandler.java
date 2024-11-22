package com.desafio_final.desafio_final.controller.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.desafio_final.desafio_final.service.exceptions.AlreadyExsistsException;
import com.desafio_final.desafio_final.service.exceptions.DatabaseException;
import com.desafio_final.desafio_final.service.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validation(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Optional<FieldError> firstError = e.getBindingResult().getFieldErrors().stream().findFirst();

        String message = firstError.map(error -> error.getDefaultMessage())
                                   .orElse(e.getMessage());

        String fieldName = firstError.map(FieldError::getField).orElse(null);

        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("nomeDoCampo", fieldName);

        return ResponseEntity.status(status).body(response);
    }

}
