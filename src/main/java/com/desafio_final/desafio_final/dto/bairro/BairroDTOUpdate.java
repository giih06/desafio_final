package com.desafio_final.desafio_final.dto.bairro;

import com.desafio_final.desafio_final.entities.Bairro;
import com.desafio_final.desafio_final.service.validation.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

public class BairroDTOUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "O codigoBairro é obrigatório")
    private Long codigoBairro;

    @NotNull(message = "O codigoMunicipio é obrigatório.")
    private Long codigoMunicipio;

    @NotBlank(message = "O nome é obrigatório.") // Não pode ser nulo ou vazio
    @Size(max = 256, message = "O nome deve ter no máximo 256 caracteres.")
    @Pattern(regexp = "^[a-zA-Zà-úÀ-ÚçÇ\\s]+$", message = "O nome deve conter apenas letras e espaços.")
    @UniqueValue(column = "nome", entity = Bairro.class, message = "Não foi possível incluir o Bairro no banco de dados. Motivo: já existe um(a) registro de bairro com o(a) esse nome cadastrado no banco de dados.")
    private String nome;

    @NotNull(message = "O status é obrigatório.") // Não pode ser nulo
    @Min(value = 1, message = "O status deve ser 1 ou 2.")
    @Max(value = 2, message = "O status deve ser 1 ou 2.")
    private Integer status;

    public BairroDTOUpdate(Bairro entity) {
        this.codigoBairro = entity.getCodigoBairro();
        this.codigoMunicipio = entity.getCodigoMunicipio();
        this.nome = entity.getNome();
        this.status = entity.getStatus();
    }

    public BairroDTOUpdate() {
    }

    public Long getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Long codigoBairro) {
        this.codigoBairro = codigoBairro;
    }

    public Long getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Long codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
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
