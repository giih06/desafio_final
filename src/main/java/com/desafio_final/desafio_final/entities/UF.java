package com.desafio_final.desafio_final.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TB_UF")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UF {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_codigouf")
    @SequenceGenerator(name="seq_codigouf", sequenceName="SEQ_CODIGOUF", allocationSize = 1)
    private Long codigoUF;

    @Column(name="sigla", length = 3)
    private String sigla;

    @Column(name="nome", length = 60)
    private String nome;

    @Column(name="status")
    private Integer status;

    // Uma unidade federativa tem vários municípios
    @OneToMany(mappedBy = "codigoUF", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
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


    @Override
    public String toString() {
        return "UF [codigoUF=" + codigoUF + ", sigla=" + sigla + ", nome=" + nome + ", status=" + status
                + "]";
    }

}
