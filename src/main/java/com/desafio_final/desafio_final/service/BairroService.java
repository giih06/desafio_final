package com.desafio_final.desafio_final.service;

import com.desafio_final.desafio_final.dto.bairro.BairroDTO;
import com.desafio_final.desafio_final.dto.bairro.BairroDTOUpdate;
import com.desafio_final.desafio_final.entities.Bairro;
import com.desafio_final.desafio_final.entities.Municipio;
import com.desafio_final.desafio_final.repository.BairroRepository;
import com.desafio_final.desafio_final.repository.MunicipioRepository;
import com.desafio_final.desafio_final.service.exceptions.DatabaseException;
import com.desafio_final.desafio_final.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BairroService {
    @Autowired
    private BairroRepository repository;

    @Autowired
    private MunicipioRepository municipioRepository;

    public List<BairroDTO> insert(BairroDTO dto) {
        Bairro entity = new Bairro();
        copyDtoToEntity(dto, entity);
        repository.save(entity);
        List<Bairro> list = repository.findAll(Sort.by("codigoBairro"));
        return list.stream().map(BairroDTO::new).collect(Collectors.toList());
    }

    public List<BairroDTO> findByCodigoBairroAndMunicipioAndNomeAndStatus(Long codigoBairro, Municipio municipio, String nome, Integer status) {
        Bairro bairro = new Bairro(codigoBairro, municipio, nome, status);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        Example<Bairro> filterbairro = Example.of(bairro, matcher);
        // Realiza a consulta com o filtro
        return repository.findAll(filterbairro).stream()
                .map(BairroDTO::new) // Converte cada entidade UF para UFDTO
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BairroDTOUpdate> update(BairroDTOUpdate dto) {
        try {
            Bairro entity = repository.getReferenceById(dto.getCodigoBairro());
            copyDtoUpdateToEntity(dto, entity);
            repository.save(entity);
            List<Bairro> list = repository.findAll(Sort.by("codigoBairro"));
            return list.stream().map(BairroDTOUpdate::new).collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(dto.getCodigoBairro());
        }
    }

    public String deleteAll() {
        try {
            repository.deleteAll(); // deleta em cascata
            return "Todos os bairros foram removidos com sucesso!";
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar deletar todos os Bairros: " + e.getMessage());
        }
    }

    private void copyDtoToEntity(BairroDTO dto, Bairro entity) {
        Municipio municipio = municipioRepository.findById(dto.getCodigoMunicipio())
                .orElseThrow(() -> new EntityNotFoundException("Municipio não encontrado para o ID: " + dto.getCodigoMunicipio()));
        entity.setMunicipio(municipio);
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
    }

    private void copyDtoUpdateToEntity(BairroDTOUpdate dto, Bairro entity) {
        Municipio municipio = municipioRepository.findById(dto.getCodigoMunicipio())
                .orElseThrow(() -> new EntityNotFoundException("Municipio não encontrado para o ID: " + dto.getCodigoMunicipio()));
        entity.setMunicipio(municipio);
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
    }

}
