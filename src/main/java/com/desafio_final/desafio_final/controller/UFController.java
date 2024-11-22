 package com.desafio_final.desafio_final.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.desafio_final.desafio_final.dto.UFDTO;
import com.desafio_final.desafio_final.service.UFService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/uf")
public class UFController {

    @Autowired
    private UFService service;

     @Transactional
     @PostMapping
     public ResponseEntity<List<UFDTO>> createUF(@Valid @RequestBody UFDTO dto) {
         List<UFDTO> dtos = service.insert(dto);
         return ResponseEntity.ok(dtos);
    }

    @PutMapping
    public ResponseEntity<List<UFDTO>> updateUF(@Valid @RequestBody UFDTO dto) {
    	List<UFDTO> dtos = service.update(dto);
        return ResponseEntity.ok().body(dtos);
    }


    /**
     * Busca UFs com base nos parâmetros fornecidos. Retorna uma lista ou um único objeto dependendo do filtro.
     *
     * @param codigoUF Código único da UF.
     * @param sigla Sigla da UF.
     * @param nome Nome da UF.
     * @param status Status da UF.
     * @return ResponseEntity contendo uma lista ou um único objeto UF.
     */
     @GetMapping
    public ResponseEntity<?> findByUF(@RequestParam(value = "codigoUF" , required = false) Long codigoUF,
                                             @RequestParam(value = "sigla" , required = false) String sigla,
                                             @RequestParam(value = "nome" , required = false) String nome,
                                             @RequestParam(value = "status" , required = false) Integer status) {
         List<UFDTO> lista = service.findByCodigoUFAndSiglaAndNomeAndStatus(codigoUF, sigla, nome, status);


         // Conta o número de parâmetros não nulos
         long paramCount = Stream.of(codigoUF, sigla, nome, status)
                 .filter(Objects::nonNull)
                 .count();

         // Se apenas o `status` for fornecido, garante que retorne uma lista
         if (paramCount == 1 && status != null) {
             return new ResponseEntity<>(lista, HttpStatus.OK);
         }

         if (lista.size() == 1) {
             return new ResponseEntity<>(lista.get(0), HttpStatus.OK);
         }

         return new ResponseEntity<>(lista, HttpStatus.OK);
     }

  
    @DeleteMapping("/{codigoUF}")
    public ResponseEntity<String> deleteById(@PathVariable Long codigoUF) {
        return ResponseEntity.ok(service.deleteById(codigoUF));
    }
}
