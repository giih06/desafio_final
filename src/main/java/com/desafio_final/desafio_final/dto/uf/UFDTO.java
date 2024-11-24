package com.desafio_final.desafio_final.dto.uf;

import com.desafio_final.desafio_final.entities.UF;
import com.desafio_final.desafio_final.service.validation.UniqueValue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UFDTO {

    private Long codigoUF;

    @NotBlank(message = "A sigla é obrigatória.") // Não pode ser nulo ou vazio
    @Size(max = 3, message = "A sigla deve ter no máximo 3 caracteres.")
    @UniqueValue(column = "sigla", entity = UF.class, message = "Não foi possível incluir UF no banco de dados. Motivo: já existe um(a) registro de UF com o(a) essa sigla cadastrado no banco de dados.")
    private String sigla;

    @NotBlank(message = "O nome é obrigatório.") // Não pode ser nulo ou vazio
    @Size(max = 60, message = "O nome deve ter no máximo 60 caracteres.")
    @Pattern(regexp = "^[a-zA-Zà-úÀ-ÚçÇ\\s]+$", message = "O nome deve conter apenas letras e espaços.")
    @UniqueValue(column = "nome", entity = UF.class, message = "Não foi possível incluir UF no banco de dados. Motivo: já existe um(a) registro de UF com o(a) esse nome cadastrado no banco de dados.")
    private String nome;

    @NotNull(message = "O status é obrigatório.") // Não pode ser nulo
    @Min(value = 1, message = "O status deve ser 1 ou 2.")
    @Max(value = 2, message = "O status deve ser 1 ou 2.")
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
