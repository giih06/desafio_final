package com.desafio_final.desafio_final.service.exceptions;

public class RequierdException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    private final String nomeDoCampo;

    // implementar nomeDoCampo
    public RequierdException(String entidade, String nomeDoCampo, String operacao) {
        // operação: incluir ou alterar
        super("Não foi possível "+operacao+" "+entidade+" no banco de dados. Motivo: O campo "+nomeDoCampo+"  é obrigatório.");
        this.nomeDoCampo = nomeDoCampo;
    }

    public String getNomeDoCampo() {
        return nomeDoCampo;
    }


}
