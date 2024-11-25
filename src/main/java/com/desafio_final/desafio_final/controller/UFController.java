 package com.desafio_final.desafio_final.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.desafio_final.desafio_final.dto.uf.UFDTOUpdate;
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

import com.desafio_final.desafio_final.dto.uf.UFDTO;
import com.desafio_final.desafio_final.service.UFService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
 /**
  * Controller responsável por gerenciar operações relacionadas às Unidades Federativas (UF).
  * <p>
  * Este controlador fornece endpoints para operações de busca, criação, atualização e exclusão
  * de Unidades Federativas. Suporta a busca por múltiplos critérios e retorna dados formatados
  * como {@link UFDTO}.
  * </p>
  *
  * Endpoints disponíveis:
  * - GET /uf: Busca Unidades Federativas com base em critérios de consulta.
  * - POST /uf: Cria uma nova Unidade Federativa.
  * - PUT /uf: Atualiza uma Unidade Federativa existente.
  * - DELETE /uf/{codigoUF}: Exclui uma Unidade Federativa pelo código.
  *
  * @author Giovanna Federico
  * @version 1.0
  * @since 2024-11-22
  */
@RestController
@RequestMapping("/uf")
public class UFController {

     private final UFService ufService;

     public UFController(UFService ufService) {
         this.ufService = ufService;
     }

     @GetMapping
     public Object findByUF(@RequestParam(value = "codigoUF" , required = false) Long codigoUF,
                                       @RequestParam(value = "sigla" , required = false) String sigla,
                                       @RequestParam(value = "nome" , required = false) String nome,
                                       @RequestParam(value = "status" , required = false) Integer status) {
         return ufService.findByCodigoUFOrSiglaOrNomeOrStatus(codigoUF, sigla, nome, status);
     }

     @Transactional
     @PostMapping
     public ResponseEntity<List<UFDTO>> createUF(@Valid @RequestBody UFDTO dto) {
         List<UFDTO> dtos = ufService.insert(dto);
         return ResponseEntity.ok(dtos);
    }

    @PutMapping
    public ResponseEntity<List<UFDTOUpdate>> updateUF(@Valid @RequestBody UFDTOUpdate dto) {
    	List<UFDTOUpdate> dtos = ufService.update(dto);
        return ResponseEntity.ok().body(dtos);
    }
  
    @DeleteMapping("/{codigoUF}")
    public ResponseEntity<String> deleteById(@PathVariable Long codigoUF) {
        return ResponseEntity.ok(ufService.deleteById(codigoUF));
    }
}
