package com.desafio_final.desafio_final.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public ResourceNotFoundException(Object id) {
        super("Recurso não encontrado. Id: " + id);
    }

    public ResourceNotFoundException(String entidade, String tipo, String valor) {
        super("Não foi possível atualizar "+entidade+" no banco de dados. Motivo: Não existe um(a) registro de UF com o(a) "+tipo+" "+ valor +" cadastrado no banco de dados.");
    }

    public ResourceNotFoundException(String entidade, String tipo, Long valor) {
        super("Não foi possível atualizar "+entidade+" no banco de dados. Motivo: Não existe um(a) registro de UF com o(a) "+tipo+" "+ valor +" cadastrado no banco de dados.");
    }


}
