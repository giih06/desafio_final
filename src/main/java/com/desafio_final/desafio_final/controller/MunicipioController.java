package com.desafio_final.desafio_final.controller;

import com.desafio_final.desafio_final.dto.municipio.MunicipioDTO;
import com.desafio_final.desafio_final.dto.municipio.MunicipioDTOUpdate;
import com.desafio_final.desafio_final.service.MunicipioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/municipio")
public class MunicipioController {

    private final MunicipioService municipioService;

    public MunicipioController(MunicipioService municipioService) {
        this.municipioService = municipioService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object findByMunicipio(@RequestParam(value = "codigoMunicipio" , required = false) Long codigoMunicipio,
                                      @RequestParam(value = "codigoUF" , required = false) Long codigoUF,
                                      @RequestParam(value = "nome" , required = false) String nome,
                                      @RequestParam(value = "status" , required = false) Integer status) {
        return municipioService.findByCodigoMunicipioOrCodigoUFOrNomeOrStatus(codigoMunicipio, codigoUF, nome, status);
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
