package com.desafio_final.desafio_final.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;


@Entity
@Table(name = "TB_BAIRRO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonPropertyOrder({"codigoBairro", "codigoMunicipio", "nome", "status"}) // Define a ordem dos campos no JSON
public class Bairro {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq_codigobairro")
    @SequenceGenerator(name="seq_codigobairro", sequenceName="SEQ_CODIGOBAIRRO", allocationSize = 1)
    private Long codigoBairro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigoMunicipio", nullable = false, referencedColumnName = "codigoMunicipio")
    @JsonIgnore
    private Municipio municipio;

    @JsonProperty("codigoMunicipio") // Serializa apenas o ID de UF
    public Long getCodigoMunicipio() {
        return municipio != null ? municipio.getCodigoMunicipio() : null;
    }

    @Column(name = "nome", length = 256)
    private String nome;

    @Column(name = "status")
    private Integer status;

    public Bairro() {
        super();
    }

    public Bairro(Long codigoBairro, Municipio municipio, String nome, Integer status) {
        this.codigoBairro = codigoBairro;
        this.municipio = municipio;
        this.nome = nome;
        this.status = status;
    }

    public Long getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Long codigoBairro) {
        if (codigoBairro > 0) {
            this.codigoBairro = codigoBairro;
        }
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
        if (status == 1 || status == 2) {
            this.status = status;
        }
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio uf) {
        this.municipio = uf;
    }

}
