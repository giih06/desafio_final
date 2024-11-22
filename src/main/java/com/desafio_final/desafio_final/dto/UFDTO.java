package com.desafio_final.desafio_final.dto;

import java.io.Serializable;

import com.desafio_final.desafio_final.entities.UF;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UFDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long codigoUF;

    @NotBlank(message = "A sigla é obrigatória.") // Não pode ser nulo ou vazio
    @Size(max = 3, message = "A sigla deve ter no máximo 3 caracteres.") // Limita o tamanho
    private String sigla;

    @NotBlank(message = "O nome é obrigatório.") // Não pode ser nulo ou vazio
    @Size(max = 60, message = "O nome deve ter no máximo 60 caracteres.") // Limita o tamanho
    @Pattern(regexp = "^[a-zA-Zà-úÀ-ÚçÇ\\s]+$", message = "O nome deve conter apenas letras e espaços.") // Apenas letras
    private String nome;

    @NotNull(message = "O status é obrigatório.") // Não pode ser nulo
    @Min(value = 1, message = "O status deve ser 1 ou 2.") // Valor mínimo permitido
    @Max(value = 2, message = "O status deve ser 1 ou 2.") // Valor máximo permitido
    private Integer status;

    public UFDTO() {
    }

    public UFDTO(UF entity) {
        this.codigoUF = entity.getCodigoUF();
        this.sigla = entity.getSigla();
        this.nome = entity.getNome();
        this.status = entity.getStatus();
    }


    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
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
}
