package com.desafio_final.desafio_final.dto.municipio;

import com.desafio_final.desafio_final.entities.Municipio;
import com.desafio_final.desafio_final.service.validation.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class MunicipioDTO {

    private Long codigoMunicipio;

    @NotNull(message = "O códigoUF é obrigatório.")
    private Long codigoUF;

    @NotBlank(message = "O nome é obrigatório.") // Não pode ser nulo ou vazio
    @Size(max = 256, message = "O nome deve ter no máximo 256 caracteres.")
    @Pattern(regexp = "^[a-zA-Zà-úÀ-ÚçÇ\\s]+$", message = "O nome deve conter apenas letras e espaços.")
    @UniqueValue(column = "nome", entity = Municipio.class, message = "Não foi possível incluir o Município no banco de dados. Motivo: já existe um(a) registro de município com o(a) esse nome cadastrado no banco de dados.")
    private String nome;

    @NotNull(message = "O status é obrigatório.") // Não pode ser nulo
    @Min(value = 1, message = "O status deve ser 1 ou 2.")
    @Max(value = 2, message = "O status deve ser 1 ou 2.")
    private Integer status;

    public MunicipioDTO(Municipio entity) {
        this.codigoMunicipio = entity.getCodigoMunicipio();
        this.codigoUF = entity.getCodigoUF();
        this.nome = entity.getNome();
        this.status = entity.getStatus();
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
}
