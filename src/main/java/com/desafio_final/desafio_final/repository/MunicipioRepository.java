package com.desafio_final.desafio_final.repository;

import com.desafio_final.desafio_final.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    // validação nome único
    boolean existsByNome(String nome);
}
