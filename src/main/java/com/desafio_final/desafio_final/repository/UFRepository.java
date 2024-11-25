package com.desafio_final.desafio_final.repository;

import com.desafio_final.desafio_final.entities.Municipio;
import com.desafio_final.desafio_final.entities.UF;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UFRepository extends JpaRepository<UF, Long> {
    List<UF> findByCodigoUFOrSiglaOrNomeOrStatus (Long codigoUF, String sigla, String nome, Integer status);

}
