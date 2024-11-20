package com.desafio_final.desafio_final.repository;

import com.desafio_final.desafio_final.entities.UF;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UFRepository extends JpaRepository<UF, Long> {

    // validação sigla única
    boolean existsBySigla(String sigla);

    // validação nome único
    boolean existsByNome(String nome);



}
