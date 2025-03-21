package com.desafio_final.desafio_final.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.List;

@Entity
@Table(name = "TB_UF")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UF {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_codigouf")
    @SequenceGenerator(name="seq_codigouf", sequenceName="SEQ_CODIGOUF", allocationSize = 1)
    private Long codigoUF;

    @Column(name="sigla", length = 3, unique = true)
    private String sigla;

    @Column(name="nome", length = 60, unique = true)
    private String nome;

    @Column(name="status")
    private Integer status;

    @OneToMany(mappedBy = "uf", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Municipio> listaMunicipios;

    public UF() {
        super();
    }

    public UF(Long codigoUF, String sigla, String nome, Integer status) {
        this.codigoUF = codigoUF;
        this.sigla = sigla;
        this.nome = nome;
        this.status = status;
    }


    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        if (codigoUF > 0) {
            this.codigoUF = codigoUF;
        }
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
        if (status == 1 || status == 2) {
            this.status = status;
        }
    }
}
