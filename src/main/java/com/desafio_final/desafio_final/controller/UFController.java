 package com.desafio_final.desafio_final.controller;

import com.desafio_final.desafio_final.dto.UFDTO;
import com.desafio_final.desafio_final.service.UFService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
@RequestMapping("/uf")
public class UFController {

    @Autowired
    private UFService service;

    /**
     * Cria uma nova UF e retorna a lista completa de UFs no banco de dados.
     *
     * @param dto Objeto UF a ser criado.
     * @return ResponseEntity contendo a URI do novo recurso e a lista atualizada de UFs.

    */
     @Transactional
     @PostMapping
     public ResponseEntity<List<UFDTO>> createUF(@Valid @RequestBody UFDTO dto) {
         dto = service.insert(dto);
         URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(dto.getCodigoUF())
                 .toUri();
         return ResponseEntity.created(uri).body(service.findAll());
    }
    /**
     * Cria uma lista de UFs e retorna a lista completa de UFs no banco de dados.
     * ufs Lista de objetos UF a ser criada.
     * @return ResponseEntity contendo a lista atualizada de UFs.
    */
    @PostMapping("/list")
    public ResponseEntity<List<UFDTO>> createListUFs(@RequestBody List<UFDTO> dto) {
        List<UFDTO> savedDtos = service.insertList(dto);
        return ResponseEntity.ok(savedDtos);
    }

    /**
     * Atualiza uma UF existente e retorna a lista completa de UFs
     *  Objeto UF com as atualizações.
     * @return ResponseEntity contendo a lista atualizada de UFs.
    */


    @PutMapping
    public ResponseEntity<List<UFDTO>> updateUF(@Valid @RequestBody UFDTO dto) {
        service.update(dto.getCodigoUF(), dto);
        return ResponseEntity.ok().body(service.findAll());
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

    /**
     * Exclui uma UF com base no código fornecido.
     *
     * @param codigoUF Código único da UF a ser excluída.
     * @return ResponseEntity contendo uma mensagem de sucesso.
     */
    @DeleteMapping("/{codigoUF}")
    public ResponseEntity<String> deleteById(@PathVariable Long codigoUF) {
        return ResponseEntity.ok(service.deleteById(codigoUF));
    }

    /**
     * Exclui todas as UFs do banco de dados.
     *
     * @return ResponseEntity contendo uma mensagem de sucesso.
     */

    // Deleção em cascata
    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        return ResponseEntity.ok(service.deleteAll());
    }

}
