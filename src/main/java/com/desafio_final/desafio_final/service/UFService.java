package com.desafio_final.desafio_final.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.desafio_final.desafio_final.dto.uf.UFDTOUpdate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
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
    
    public Object findByCodigoUFOrSiglaOrNomeOrStatus(Long codigoUF, String sigla, String nome, Integer status) {
        List<UF> listaDeUFs = ufRepository.findByCodigoUFOrSiglaOrNomeOrStatus(codigoUF, sigla, nome, status);

        if (listaDeUFs.isEmpty()) {
            return List.of();
        }

        // Conta o número de parâmetros não nulos
        long paramCount = Stream.of(codigoUF, sigla, nome, status)
                .filter(Objects::nonNull)
                .count();

        // Se apenas o status for fornecido, garante que retorne uma lista
        if (paramCount == 1 && status != null) {
            return listaDeUFs.isEmpty() ? List.of() : listaDeUFs;
        }

        if (listaDeUFs.size() == 1) {
            return listaDeUFs.get(0);
        }
        return listaDeUFs;
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

