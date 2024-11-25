package com.desafio_final.desafio_final.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.desafio_final.desafio_final.dto.uf.UFDTOUpdate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.desafio_final.desafio_final.dto.uf.UFDTO;
import com.desafio_final.desafio_final.entities.UF;
import com.desafio_final.desafio_final.repository.UFRepository;
import com.desafio_final.desafio_final.service.exceptions.DatabaseException;
import com.desafio_final.desafio_final.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UFService {

    private final UFRepository ufRepository;

    public UFService(UFRepository ufRepository) {
        this.ufRepository = ufRepository;
    }

    public List<UFDTO> insert(UFDTO dto) {
        UF entity = new UF();
        copyDtoToEntity(dto, entity);
        ufRepository.save(entity);
        List<UF> list = ufRepository.findAll(Sort.by("codigoUF"));
        return list.stream().map(UFDTO::new).collect(Collectors.toList());
    }

    public List<UFDTO> findByCodigoUFAndSiglaAndNomeAndStatus(Long codigoUF, String sigla, String nome, Integer status) {
        // cria o objeto uf com todos os parâmetros recebidos
        UF uf = new UF(codigoUF, sigla, nome, status);
        // Define o matcher para o filtro
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues() // Ignora valores nulos
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Permite busca parcial em strings
                .withIgnoreCase(); // Ignora maiúsculas e minúsculas
        // Cria o exemplo baseado no objeto uf e no matcher
        Example<UF> filteruf = Example.of(uf, matcher);
        // Realiza a consulta com o filtro
        return ufRepository.findAll(filteruf).stream()
                .map(UFDTO::new) // Converte cada entidade UF para UFDTO
                .collect(Collectors.toList());
    }


    @Transactional
    public List<UFDTOUpdate> update(UFDTOUpdate dto) {
        try {
            UF entity = ufRepository.getReferenceById(dto.getCodigoUF());
            copyDtoUpdateToEntity(dto, entity);
            ufRepository.save(entity);
            List<UF> list = ufRepository.findAll(Sort.by("codigoUF"));
            return list.stream().map(UFDTOUpdate::new).collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(dto.getCodigoUF());
        }
    }

    public String deleteById(@PathVariable Long codigoUF) {
        if (ufRepository.existsById(codigoUF)) {
            try {
                ufRepository.deleteById(codigoUF);
                return "UF " + codigoUF + " removido com sucesso!";
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseException(e.getMessage());
            }
        } else {
            throw new ResourceNotFoundException(codigoUF);
        }
    }

    private void copyDtoToEntity(UFDTO dto, UF entity) {
        entity.setSigla(dto.getSigla());
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
    }

    private void copyDtoUpdateToEntity(UFDTOUpdate dto, UF entity) {
        entity.setSigla(dto.getSigla());
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
    }
}

