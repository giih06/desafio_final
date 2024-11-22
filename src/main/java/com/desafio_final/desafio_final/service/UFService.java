package com.desafio_final.desafio_final.service;

import java.util.List;
import java.util.stream.Collectors;

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

    /// MÉTODOS DE VALIDAÇÃO ///


    /*private void validaCodigoUF(UF uf) {
         verifica se o campo codigoUF é vazio
        if (uf.getCodigoUF() == null) {
            throw new RequierdException("UF", "codigoUF", "alterar");
        }
         Verifica se o campo codigoUF contem apenas números
        if(!uf.getCodigoUF().toString().matches("^[0-9]+$")) {
            throw new InvalidFormatException("codigoUF", "números");
        }
    }

    private void validaNome(UF uf, String operacao) {
         verifica se o campo nome é vazio
        if (uf.getNome() == null || uf.getNome().isEmpty()) {
            throw new RequierdException("UF", "nome", operacao);
        }
         Verifica se o campo nome contem apenas letras e espaços (sem números ou caracteres especiais)
        if(!uf.getNome().matches("^[a-zA-Zà-úÀ-ÚçÇ\\s]+$")) {
            throw new InvalidFormatException("nome", "letras e espaços.");
        }
    }

    private void validaSigla(UF uf, String operacao) {
         verifica se o campo sigla é vazio
        if (uf.getSigla() == null || uf.getSigla().isEmpty()) {
            throw new RequierdException("UF", "sigla", operacao);
        }
        Verifica se o campo sigla contem apenas letras e espaços (sem números ou caracteres especiais)
        if(!uf.getSigla().matches("^[a-zA-Zà-úÀ-ÚçÇ\\s]+$")) {
            throw new InvalidFormatException("sigla", "letras e espaços.");
        }
    }

     verifica se o status for nulo
    private void validaStatus(UF uf, String operacao) {
        if (uf.getStatus() == null) {
            throw new RequierdException("UF", "status", operacao);
        }
    }

     Verifica se já existe uma unidade federativa com o mesmo nome
    public void verificaSeNomeJaExiste(String nome) {
        if (repository.existsByNome(nome)) {
            throw new AlreadyExsistsException("UF", "nome", nome);
        }
    }

    Verifica se já existe uma unidade federativa a mesma sigla
    private void verificaSeSiglaJaExiste(String sigla) {
        if (repository.existsBySigla(sigla)) {
            throw new AlreadyExsistsException("UF", "sigla", sigla);
        }
    }

    private void validaUF(UF uf, String operacao) {
        validaStatus(uf, operacao);
        validaSigla(uf, operacao);
        validaNome(uf, operacao);
        verificaSeSiglaJaExiste(uf.getSigla());
        verificaSeNomeJaExiste(uf.getNome());
    }
    */

    public List<UFDTO> insert(UFDTO dto) {  	
        UF entity = new UF();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        List<UF> list = repository.findAll(Sort.by("codigoUF"));
        return list.stream().map(x -> new UFDTO(x)).collect(Collectors.toList());
    }

    public List<UFDTO> findAll() {
        List<UF> list = repository.findAll(Sort.by("codigoUF"));
        return list.stream().map(x -> new UFDTO(x)).collect(Collectors.toList());
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
        return repository.findAll(filteruf).stream()
                .map(UFDTO::new) // Converte cada entidade UF para UFDTO
                .collect(Collectors.toList());    
    }

    @Transactional
    public List<UFDTO> update(UFDTO dto) {
        try {
            UF entity = repository.getReferenceById(dto.getCodigoUF());
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

