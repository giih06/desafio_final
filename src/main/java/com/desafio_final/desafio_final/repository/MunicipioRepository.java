package com.desafio_final.desafio_final.repository;

import com.desafio_final.desafio_final.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

    List<Municipio> findByCodigoMunicipioOrUf_CodigoUFOrNomeOrStatus(Long codigoMunicipio, Long codigoUF, String nome, Integer status);
}
