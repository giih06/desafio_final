package com.desafio_final.desafio_final.controller;

import com.desafio_final.desafio_final.dto.bairro.BairroDTO;
import com.desafio_final.desafio_final.dto.bairro.BairroDTOUpdate;
import com.desafio_final.desafio_final.entities.Municipio;
import com.desafio_final.desafio_final.service.BairroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("/bairro")
public class BairroController {

    private final BairroService bairroService;

    public BairroController(BairroService bairroService) {
        this.bairroService = bairroService;
    }

    @GetMapping
    public ResponseEntity<?> findByBairro(@RequestParam(value = "codigoBairro" , required = false) Long codigoBairro,
                                          @RequestParam(value = "codigoMunicipio" , required = false) Municipio codigoMunicipio,
                                          @RequestParam(value = "nome" , required = false) String nome,
                                          @RequestParam(value = "status" , required = false) Integer status) {
        // concertar essa parte do codigo ( se o parâmetro de pesquiosa for status
        List<BairroDTO> lista = bairroService.findByCodigoBairroAndMunicipioAndNomeAndStatus(codigoBairro, codigoMunicipio, nome, status);


        // Conta o número de parâmetros não nulos
        long paramCount = Stream.of(codigoBairro, codigoMunicipio, nome, status)
                .filter(Objects::nonNull)
                .count();

        // Se apenas o codigoMunicipio for , garante que retorne uma lista
        if (paramCount == 1 && codigoBairro != null) {
            return new ResponseEntity<>(lista.get(0), HttpStatus.OK);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<List<BairroDTO>> createMunicipio(@Valid @RequestBody BairroDTO dto) {
        List<BairroDTO> dtos = bairroService.insert(dto);
        return ResponseEntity.ok(dtos);
    }

    @PutMapping
    public ResponseEntity<List<BairroDTOUpdate>> updateMunicipio(@Valid @RequestBody BairroDTOUpdate dto) {
        List<BairroDTOUpdate> dtos = bairroService.update(dto);
        return ResponseEntity.ok().body(dtos);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllBairros() {
        return ResponseEntity.ok(bairroService.deleteAll());
    }

}
