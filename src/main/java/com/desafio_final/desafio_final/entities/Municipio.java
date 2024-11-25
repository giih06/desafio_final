package com.desafio_final.desafio_final.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.List;

@Entity
@Table(name = "TB_MUNICIPIO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonPropertyOrder({"codigoMunicipio", "codigoUF", "nome", "status"}) // Define a ordem dos campos no JSON
public class Municipio {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq_codigomunicipio")
    @SequenceGenerator(name="seq_codigomunicipio", sequenceName="SEQ_CODIGOMUNICIPIO", allocationSize = 1)
    private Long codigoMunicipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigoUF", nullable = false, referencedColumnName = "codigoUF")
    @JsonIgnore
    private UF uf;

    @JsonProperty("codigoUF") // Serializa apenas o ID de UF
    public Long getCodigoUF() {
        return uf != null ? uf.getCodigoUF() : null;
    }

    @Column(name = "nome", length = 256)
    private String nome;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "municipio", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Bairro> listaBairros;

    public Municipio() {
        super();
    }

    public Municipio(Long codigoMunicipio, UF uf, String nome, Integer status) {
        this.codigoMunicipio = codigoMunicipio;
        this.uf = uf;
        this.nome = nome;
        this.status = status;
    }

    public Long getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Long codigoMunicipio) {
        if (codigoMunicipio > 0) {
            this.codigoMunicipio = codigoMunicipio;
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

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }
}
