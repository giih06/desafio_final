package com.desafio_final.desafio_final.controller.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.desafio_final.desafio_final.service.exceptions.DatabaseException;
import com.desafio_final.desafio_final.service.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ControllerExceptionHandler {

    // ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandartError> constraintViolation(ConstraintViolationException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Extrair apenas a mensagem de erro
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> violation.getMessage()) // Pega apenas a mensagem de validação
                .findFirst() // Considera apenas a primeira violação, caso haja múltiplas
                .orElse("Erro de validação"); // Mensagem padrão se nenhuma for encontrada

        StandartError se = new StandartError(
                errorMessage,
                status.value());

        return ResponseEntity.status(status).body(se);
    }


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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandartError> methodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandartError se = new StandartError();
        se.setMensagem("O campo " + e.getName() +
                " espera um valor de tipo " + e.getRequiredType().getSimpleName() +
                " e foi informado " + e.getValue() + " de tipo " + e.getValue().getClass().getSimpleName());
        se.setStatus(status.value());
        return ResponseEntity.status(status).body(se);
    }

}
