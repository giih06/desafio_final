package com.desafio_final.desafio_final.service;

import java.util.List;
import java.util.stream.Collectors;

import com.desafio_final.desafio_final.service.exceptions.AlreadyExsistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.desafio_final.desafio_final.dto.UFDTO;
import com.desafio_final.desafio_final.entities.UF;
import com.desafio_final.desafio_final.repository.UFRepository;
import com.desafio_final.desafio_final.service.exceptions.DatabaseException;
import com.desafio_final.desafio_final.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UFService {
    @Autowired
    private UFRepository repository;

    // Verifica se já existe um uf com o mesmo nome
    public void verificaSeNomeJaExiste(String nome) {
        if (repository.existsByNome(nome)) {
            throw new AlreadyExsistsException("UF", "nome", nome);
        }
    }
    // Verifica se já existe um uf com a mesma sigla
    public void verificaSeSiglaJaExiste(String sigla) {
        if (repository.existsBySigla(sigla)) {
            throw new AlreadyExsistsException("UF", "sigla", sigla);
        }
    }

    public List<UFDTO> insert(UFDTO dto) {
        verificaSeNomeJaExiste(dto.getNome());
        verificaSeSiglaJaExiste(dto.getSigla());
        UF entity = new UF();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        List<UF> list = repository.findAll(Sort.by("codigoUF"));
        return list.stream().map(x -> new UFDTO(x)).collect(Collectors.toList());
    }

    /*public List<UFDTO> findAll() {
        List<UF> list = repository.findAll(Sort.by("codigoUF"));
        return list.stream().map(x -> new UFDTO(x)).collect(Collectors.toList());
    }*/
    
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
        return repository.findAll(filteruf).stream()
                .map(UFDTO::new) // Converte cada entidade UF para UFDTO
                .collect(Collectors.toList());    
    }

    @Transactional
    public List<UFDTO> update(UFDTO dto) {
        try {
            UF entity = repository.getReferenceById(dto.getCodigoUF());
            if (!entity.getNome().equals(dto.getNome())) {
                verificaSeNomeJaExiste(dto.getNome());
            }
            if (!entity.getSigla().equals(dto.getSigla())) {
                verificaSeSiglaJaExiste(dto.getSigla());
            }
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            List<UF> list = repository.findAll(Sort.by("codigoUF"));
            return list.stream().map(x -> new UFDTO(x)).collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(dto.getCodigoUF());
        }
    }

    public String deleteById(@PathVariable Long codigoUF) {
        if (repository.existsById(codigoUF)) {
            try {
                repository.deleteById(codigoUF);
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
}

