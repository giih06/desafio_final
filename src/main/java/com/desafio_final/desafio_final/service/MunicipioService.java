package com.desafio_final.desafio_final.service;

import com.desafio_final.desafio_final.dto.UFDTO;
import com.desafio_final.desafio_final.entities.Municipio;
import com.desafio_final.desafio_final.entities.UF;
import com.desafio_final.desafio_final.repository.MunicipioRepository;
import com.desafio_final.desafio_final.repository.UFRepository;
import com.desafio_final.desafio_final.service.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MunicipioService {
    @Autowired
    private MunicipioRepository repository;

    @Autowired
    private UFRepository ufRepository;


    /// MÉTODOS DE VALIDAÇÃO ///

    private void validaCodigoMunicipio(Municipio municipio, String operacao) {
        // verifica se o campo codigoMunicipio é vazio
        if (municipio.getCodigoMunicipio() == null) {
            throw new RequierdException("Municipio", "codigoMunicipio", operacao);
        }
        // Verifica se o campo codigoMunicipio contem apenas números
        if(!municipio.getCodigoMunicipio().toString().matches("^[0-9]+$")) {
            throw new InvalidFormatException("codigoMunicipio", "números");
        }
    }

    private void validaCodigoUF(Municipio municipio, String operacao) {
        // verifica se o campo codigoUF é vazio
        if (municipio.getCodigoUF() == null) {
            throw new RequierdException("Municipio", "codigoUF", operacao);
        }
        // Verifica se o campo codigoUF contem apenas números
        if(!municipio.getCodigoUF().toString().matches("^[0-9]+$")) {
            throw new InvalidFormatException("codigoUF", "números");
        }
    }

    private void validaNome(Municipio municipio, String operacao) {
        // verifica se o campo nome é vazio
        if (municipio.getNome() == null || municipio.getNome().isEmpty()) {
            throw new RequierdException("Municipio", "nome", operacao);
        }
        // Verifica se o campo nome contem apenas letras e espaços (sem números ou caracteres especiais)
        if(!municipio.getNome().matches("^[a-zA-Zà-úÀ-ÚçÇ\\s]+$")) {
            throw new InvalidFormatException("nome", "letras e espaços.");
        }
    }

    // verifica se o status for nulo
    private void validaStatus(Municipio municipio, String operacao) {
        if (municipio.getStatus() == null) {
            throw new RequierdException("Municipio", "status", operacao);
        }
    }

    // Verifica se já existe um municipio com o mesmo nome
    public void verificaSeNomeJaExiste(String nome) {
        if (repository.existsByNome(nome)) {
            throw new AlreadyExsistsException("Municipio", "nome", nome);
        }
    }

    private void validaMunicipio(Municipio municipio, String operacao) {
            validaCodigoUF(municipio, operacao);
            validaNome(municipio, operacao);
            validaStatus(municipio, operacao);
            verificaSeNomeJaExiste(municipio.getNome());
    }



    /// MÉTODOS DO POST ///
    //  Salva um município apenas se o nome não existir e existir um valor no campo
    public Municipio cadastrarMunicipio(Municipio municipio) {
        validaMunicipio(municipio, "inserir");

        // Buscando a UF com base no codigoUF fornecido no JSON
        UF uf = ufRepository.findById(municipio.getCodigoUF())
                .orElseThrow(() -> new ResourceNotFoundException("UF", "codigoUF", municipio.getCodigoUF()));

        // Associando a UF ao objeto Municipio
        municipio.setUf(uf);

        // Agora podemos salvar o Municipio com a UF associada corretamente
        return repository.save(municipio);
    }


    public List<Municipio> cadastrarListaMunicipios(List<Municipio> municipios) {
        for(Municipio municipio : municipios) {
            // Buscando a UF com base no codigoUF fornecido no JSON para cada município
            UF uf = ufRepository.findById(municipio.getCodigoUF())
                    .orElseThrow(() -> new ResourceNotFoundException("UF", "codigoUF", municipio.getCodigoUF()));

            // Associando a UF ao objeto Municipio
            municipio.setUf(uf);
        }
        // Salvando todos os municípios e retornando a lista já com as UFs associadas
        List<Municipio> municipiosSalvos = repository.saveAll(municipios);

        // Retorna a lista de municípios com as UFs associadas já criada
        return municipiosSalvos;
    }


    /// MÉTODOS DO GET ///
    public List<Municipio> getAllMunicipios() {
        return repository.findAll();
    }

    /*
     * Busca municípios no banco de dados com base em múltiplos critérios: código do município,
     * código da UF, nome e status. Os critérios de busca permitem valores parciais e ignoram
     * diferenças de maiúsculas/minúsculas.
     *
     * @param codigoMunicipio O código do município (opcional).
     * @param codigoUF        A unidade federativa associada ao município (opcional).
     * @param nome            O nome do município ou parte dele (opcional).
     * @param status          O status do município (ativo/inativo) (opcional).
     * @return Uma lista de municípios que correspondem aos critérios de busca.
     * @throws IllegalArgumentException Caso algum parâmetro de busca seja inválido.
     */
    public List<Municipio> findByCodigoMunicipioAndCodigoUFAndNomeAndStatus(Long codigoMunicipio, Long codigoUF, String nome, Integer status) {
        Municipio municipio = new Municipio(codigoMunicipio, codigoUF, nome, status);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        Example<Municipio> filtermunicipio = Example.of(municipio, matcher);
        return repository.findAll(filtermunicipio);
    }

    /// MÉTODOS DO UPDATE ///
    // método que atualiza os dados
    public void updateData(Municipio entity, Municipio novoMunicipio) {
        // Busca a UF existente no banco
        UF ufExistente = ufRepository.findById(novoMunicipio.getCodigoUF())
                .orElseThrow(() -> new ResourceNotFoundException("UF", "codigoUF", novoMunicipio.getCodigoUF()));

        // Atualiza os dados
        entity.setUf(ufExistente);
        entity.setNome(novoMunicipio.getNome());
        entity.setStatus(novoMunicipio.getStatus());
    }
    // método que atualiza um Municipio
    public void updateMunicipio(Long codigoMunicipio, Municipio municipio) {
        try {
            // valida os campos
            validaCodigoMunicipio(municipio, "alterar");
            validaMunicipio(municipio, "alterar");
            Municipio entity = repository.findById(codigoMunicipio)
                    .orElseThrow(() -> new ResourceNotFoundException("Municipio", "codigoMunicipio", codigoMunicipio));
            updateData(entity, municipio);
            repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(codigoMunicipio);
        }
    }

    /// MÉTODOS DO DELETE ///
    // deleta o municipio por id
    public String deleteMunicipioById(@PathVariable Long codigoMunicipio) {
        if (repository.existsById(codigoMunicipio)) {
            try {
                repository.deleteById(codigoMunicipio);
                return "Municipio " + codigoMunicipio + " removido com sucesso!";
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseException(e.getMessage());
            }
        } else {
            throw new ResourceNotFoundException(codigoMunicipio);
        }
    }

    // deleta todos os municipios
    @Transactional // sucesso ou rollback
    public String deleteAllMunicipios() {
        try {
            repository.deleteAll(); // Deleta todos os registros de TB_MUNICIPIO, aplicando cascata se configurado no modelo
            return "Todos os municipios foram removidas com sucesso!";
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao tentar deletar todos os Municipios: " + e.getMessage());
        }
    }

    private void copyDtoToEntity(UFDTO dto, UF entity) {
        entity.set(dto.getSigla());
        entity.setNome(dto.getNome());
        entity.setStatus(dto.getStatus());
    }

}
