package com.desafio_final.desafio_final.controller;

import com.desafio_final.desafio_final.dto.municipio.MunicipioDTO;
import com.desafio_final.desafio_final.dto.municipio.MunicipioDTOUpdate;
import com.desafio_final.desafio_final.entities.UF;
import com.desafio_final.desafio_final.service.MunicipioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@Validated
@RequestMapping("/municipio")
public class MunicipioController {

    private final MunicipioService municipioService;

    public MunicipioController(MunicipioService municipioService) {
        this.municipioService = municipioService;
    }


    @GetMapping
    public ResponseEntity<?> findByMunicipio(@RequestParam(value = "codigoMunicipio" , required = false) Long codigoMunicipio,
                                             @RequestParam(value = "codigoUF" , required = false) UF codigoUF,
                                             @RequestParam(value = "nome" , required = false)
                                             @Pattern(regexp = "^[a-zA-Zà-úÀ-ÚçÇ\\s]+$", message = "O nome deve conter apenas letras e espaços.")
                                             String nome,
                                             @RequestParam(value = "status" , required = false)
                                             @Min(value = 1, message = "O status deve ser 1 ou 2.")
                                             @Max(value = 2, message = "O status deve ser 1 ou 2.")
                                             Integer status) {
        // concertar essa parte do codigo ( se o parâmetro de pesquiosa for status
        List<MunicipioDTO> lista = municipioService.findByCodigoMunicipioAndUFAndNomeAndStatus(codigoMunicipio, codigoUF, nome, status);


        // Conta o número de parâmetros não nulos
        long paramCount = Stream.of(codigoMunicipio, codigoUF, nome, status)
                .filter(Objects::nonNull)
                .count();

        // Se apenas o codigoMunicipio for , garante que retorne uma lista
        if (paramCount == 1 && codigoMunicipio != null) {
            return new ResponseEntity<>(lista.get(0), HttpStatus.OK);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);

    }
    @Transactional
    @PostMapping
    public ResponseEntity<List<MunicipioDTO>> createMunicipio(@Valid @RequestBody MunicipioDTO dto) {
        List<MunicipioDTO> dtos = municipioService.insert(dto);
        return ResponseEntity.ok(dtos);
    }

    @PutMapping
    public ResponseEntity<List<MunicipioDTOUpdate>> updateMunicipio(@Valid @RequestBody MunicipioDTOUpdate dto) {
        List<MunicipioDTOUpdate> dtos = municipioService.update(dto);
        return ResponseEntity.ok().body(dtos);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllMunicipios() {
        return ResponseEntity.ok(municipioService.deleteAll());
    }


}
