package com.desafio_final.desafio_final.service;

import com.desafio_final.desafio_final.dto.UFDTO;
import com.desafio_final.desafio_final.entities.UF;
import com.desafio_final.desafio_final.repository.UFRepository;
import com.desafio_final.desafio_final.service.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

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

    /// MÉTODOS DO POST ///
    //  Salva uma unidade federativa apenas se sigla e o nome não existirem e existir um valor no campo
    public UFDTO insert(UFDTO dto) {
        UF entity = new UF();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UFDTO(entity);
    }


    /* public List<UF> cadastrarListaUFs(List<UF> ufs) {
        return repository.saveAll(ufs);
    } */
    public List<UFDTO> cadastrarListaUFs(List<UFDTO> dtos) {
        List<UF> ufs = dtos.stream().map(dto -> {
            UF entity = new UF();
            copyDtoToEntity(dto, entity);
            return entity;
        }).collect(Collectors.toList());

        return repository.saveAll(ufs).stream().map(UFDTO::new).collect(Collectors.toList());
    }



    /// MÉTODOS DO GET ///

    // Lista de todas as ufs
    public List<UFDTO> findAll() {
        List<UF> list = repository.findAll(Sort.by("codigoUF"));
        return list.stream().map(x -> new UFDTO(x)).collect(Collectors.toList());
    }
    // Busca UF por id,sigla, nome e status
    /*
    public List<UF> findByCodigoUFAndSiglaAndNomeAndStatus(Long codigoUF, String sigla, String nome, Integer status) {
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
        return repository.findAll(filteruf);
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
                .collect(Collectors.toList());    }


    /// MÉTODOS DO UPDATE ///
    // método que atualiza uma UF
    @Transactional
    public UFDTO update(Long codigoUF, UFDTO dto) {
        try {
            UF entity = repository.getReferenceById(codigoUF);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UFDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(codigoUF);
        }
    }



    /// MÉTODOS DO DELETE ///
    // deleta a uf por id
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

    // deleta todas as ufs
    @Transactional // sucesso ou rollback
    public String deleteAll() {
        try {
            repository.deleteAll(); // Deleta todos os registros de TB_UF, aplicando cascata se configurado no modelo
            return "Todas as UFs foram removidas com sucesso!";
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar deletar todas as UFs: " + e.getMessage());
        }
    }

    private void copyDtoToEntity(UFDTO dto, UF entity) {
        entity.setSigla(dto.getSigla());
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
    }
}

