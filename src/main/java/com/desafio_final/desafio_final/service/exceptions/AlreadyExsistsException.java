package com.desafio_final.desafio_final.service.exceptions;

public class AlreadyExsistsException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public AlreadyExsistsException(String entidade, String tipo, String valor) {
        super("Não foi possível incluir "+entidade+" no banco de dados. Motivo: já existe um(a) registro de UF com o(a) "+tipo+" "+valor+" cadastrado no banco de dados.");
    }
}
