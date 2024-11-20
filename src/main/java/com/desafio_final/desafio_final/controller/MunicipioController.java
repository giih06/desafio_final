package com.desafio_final.desafio_final.controller;

import com.desafio_final.desafio_final.entities.Municipio;
import com.desafio_final.desafio_final.service.MunicipioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("/municipio")
public class MunicipioController {

    @Autowired
    private MunicipioService service;

    // Create
    // adiciona um uf e retorna a lista do banco
    @Transactional
    @PostMapping
    public ResponseEntity<List<Municipio>> createMunicipio(@RequestBody Municipio municipio) {
        municipio = service.cadastrarMunicipio(municipio);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(municipio.getCodigoMunicipio())
                .toUri();
        return ResponseEntity.created(uri).body(service.getAllMunicipios());
    }

    @PostMapping("/list")
    public ResponseEntity<List<Municipio>> createListMunicipios(@RequestBody List<Municipio> municipios) {
        service.cadastrarListaMunicipios(municipios);
        return ResponseEntity.ok().body(service.getAllMunicipios());
    }

    // Read
    // Lê os dados de acordo com os parâmetros dados na url
    @GetMapping
    public ResponseEntity<?> findByUF(@RequestParam(value = "codigoMunicipio" , required = false) Long codigoMunicipio,
                                      @RequestParam(value = "codigoUF" , required = false) Long codigoUF,
                                      @RequestParam(value = "nome" , required = false) String nome,
                                      @RequestParam(value = "status" , required = false) Integer status) {
        // concertar essa parte do codigo ( se o parâmetro de pesquiosa for status
        List<Municipio> lista = service.findByCodigoMunicipioAndCodigoUFAndNomeAndStatus(codigoMunicipio, codigoUF, nome, status);


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

    @DeleteMapping
    public ResponseEntity<String> deleteAllMunicipios() {
        return ResponseEntity.ok(service.deleteAllMunicipios());
    }

    @DeleteMapping("/{codigoMunicipio}")
    public ResponseEntity<String> deleteMunicipioById(@PathVariable Long codigoMunicipio) {
        return ResponseEntity.ok(service.deleteMunicipioById(codigoMunicipio));
    }


}
