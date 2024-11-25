package com.desafio_final.desafio_final.service;

import com.desafio_final.desafio_final.dto.municipio.MunicipioDTO;
import com.desafio_final.desafio_final.dto.municipio.MunicipioDTOUpdate;
import com.desafio_final.desafio_final.entities.Municipio;
import com.desafio_final.desafio_final.entities.UF;
import com.desafio_final.desafio_final.repository.MunicipioRepository;
import com.desafio_final.desafio_final.repository.UFRepository;
import com.desafio_final.desafio_final.service.exceptions.DatabaseException;
import com.desafio_final.desafio_final.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final UFRepository ufRepository;

    public MunicipioService(MunicipioRepository municipioRepository, UFRepository ufRepository) {
        this.municipioRepository = municipioRepository;
        this.ufRepository = ufRepository;
    }

    public List<MunicipioDTO> insert(MunicipioDTO dto) {
        Municipio entity = new Municipio();
        copyDtoToEntity(dto, entity);
        municipioRepository.save(entity);
        List<Municipio> list = municipioRepository.findAll(Sort.by("codigoMunicipio"));
        return list.stream().map(MunicipioDTO::new).collect(Collectors.toList());
    }


    public List<MunicipioDTO> findByCodigoMunicipioAndUFAndNomeAndStatus(Long codigoMunicipio, UF uf, String nome, Integer status) {
        Municipio municipio = new Municipio(codigoMunicipio, uf, nome, status);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        Example<Municipio> filtermunicipio = Example.of(municipio, matcher);
        // Realiza a consulta com o filtro
        return municipioRepository.findAll(filtermunicipio).stream()
                .map(MunicipioDTO::new) // Converte cada entidade UF para UFDTO
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MunicipioDTOUpdate> update(MunicipioDTOUpdate dto) {
        try {
            Municipio entity = municipioRepository.getReferenceById(dto.getCodigoMunicipio());
            copyDtoUpdateToEntity(dto, entity);
            municipioRepository.save(entity);
            List<Municipio> list = municipioRepository.findAll(Sort.by("codigoMunicipio"));
            return list.stream().map(MunicipioDTOUpdate::new).collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(dto.getCodigoUF());
        }
    }

    public String deleteAll() {
        try {
            municipioRepository.deleteAll(); // Deleta todos os registros de TB_MUNICIPIO, aplicando cascata se configurado no modelo
            return "Todos os municipios foram removidas com sucesso!";
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar deletar todos os Municipios: " + e.getMessage());
        }
    }

    private void copyDtoToEntity(MunicipioDTO dto, Municipio entity) {
        UF uf = ufRepository.findById(dto.getCodigoUF())
                .orElseThrow(() -> new EntityNotFoundException("UF não encontrada para o ID: " + dto.getCodigoUF()));
        entity.setUf(uf);
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
    }

    private void copyDtoUpdateToEntity(MunicipioDTOUpdate dto, Municipio entity) {
        UF uf = ufRepository.findById(dto.getCodigoUF())
                .orElseThrow(() -> new EntityNotFoundException("UF não encontrada para o ID: " + dto.getCodigoUF()));
        entity.setUf(uf);
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
    }

}
