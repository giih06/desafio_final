package com.desafio_final.desafio_final.service.exceptions;

public  class DatabaseException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public DatabaseException(String msg) {
        super(msg);
    }
}

