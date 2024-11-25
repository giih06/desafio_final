package com.desafio_final.desafio_final.controller.exceptions;

public class MissingFieldError {

    private String mensagem;
    private int status;
    private String nomeDoCampo;

    public MissingFieldError(String mensagem, int status, String nomeDoCampo) {
        this.mensagem = mensagem;
        this.status = status;
        this.nomeDoCampo = nomeDoCampo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNomeDoCampo() {
        return nomeDoCampo;
    }

    public void setNomeDoCampo(String nomeDoCampo) {
        this.nomeDoCampo = nomeDoCampo;
    }
}
