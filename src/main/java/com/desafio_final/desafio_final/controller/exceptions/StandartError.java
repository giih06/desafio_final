package com.desafio_final.desafio_final.controller.exceptions;

public class StandartError {

    public StandartError() {}

    public StandartError(String mensagem, int status) {
        this.mensagem = mensagem;
        this.status = status;
    }

    private String mensagem;
    private int status;

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
}
