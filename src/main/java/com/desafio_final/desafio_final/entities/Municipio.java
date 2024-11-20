package com.desafio_final.desafio_final.entities;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_MUNICIPIO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonPropertyOrder({"codigoMunicipio", "codigoUF", "nome", "status"}) // Define a ordem dos campos no JSON
public class Municipio {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq_codigomunicipio")
    @SequenceGenerator(name="seq_codigomunicipio", sequenceName="SEQ_CODIGOMUNICIPIO", allocationSize = 1)
    private Long codigoMunicipio;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigoUF", nullable = false, referencedColumnName = "codigoUF")
    private UF uf;

    @JsonProperty("codigoUF")
    @Column(name = "codigoUF", insertable = false, updatable = false)  // Impede a inserção e atualização na coluna 'codigoUF'
    private Long codigoUF;

    @Column(name = "nome", length = 256)
    private String nome;

    @Column(name = "status")
    private Integer status;

    public Municipio() {
        super();
    }

    public Municipio(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {
        this.codigoMunicipio = codigoMunicipio;
        this.codigoUF = codigoUF;
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

    // Getter e setter para associar o ID de UF com o objeto UF
    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
        // Aqui você pode definir a UF com o ID recebido
        // A parte de associar a UF com o código recebido será feita pelo JPA automaticamente.
    }

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }


    @Override
    public String toString() {
        return "Municipio{" +
                "codigoMunicipio=" + codigoMunicipio +
                ", codigoUF=" + codigoUF +
                ", nome='" + nome + '\'' +
                ", status=" + status +
                '}';
    }
}
