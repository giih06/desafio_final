package com.desafio_final.desafio_final.controller.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError {
    private static final long serialVersionUID = 1L;

    private List<MissingFieldError> errors = new ArrayList<>();

    public List<MissingFieldError> getErrors() {
        return errors;
    }

    public void addError(String message, int status, String nomeDoCampo) {
        errors.add(new MissingFieldError(message, status, nomeDoCampo));
    }
}
