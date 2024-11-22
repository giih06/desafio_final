package com.desafio_final.desafio_final.dto;

import java.io.Serializable;

public class MunicipioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigoMunicipio;

    private Long codigoUF;

    private String nome;

    private Integer status;

    public MunicipioDTO(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {
        this.codigoMunicipio = codigoMunicipio;
        this.codigoUF = codigoUF;
        this.nome = nome;
        this.status = status;
    }

    public MunicipioDTO() {
    }

    public Long getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Long codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MunicipioDTO{" +
                "codigoMunicipio=" + codigoMunicipio +
                ", codigoUF=" + codigoUF +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                '}';
    }
}
