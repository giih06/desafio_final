package com.desafio_final.desafio_final.service.exceptions;

public class InvalidFormatException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public InvalidFormatException(String nomeDoCampo, String mensagemAdicional) {
        super("O(a) "+nomeDoCampo+" deve conter apenas " + mensagemAdicional);
    }

}
