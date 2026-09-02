package com.bancodigital.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodigital.api.config.SwaggerConfig;
import com.bancodigital.api.dto.ContaDto;
import com.bancodigital.api.model.Conta;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/contas")
@Tag(name = SwaggerConfig.CONTAS_TAG, description = "Endpoints para gerenciamento de contas")
public class ContasController {

    @GetMapping
    @Operation(summary = "Buscar todas as contas")
    public ResponseEntity<List<Conta>> buscarContas() {
        return ResponseEntity.ok(List.of(new Conta(1L, "teste1"), new Conta(2L, "teste2"), new Conta(3L, "teste3")));
    }

    @GetMapping(path = {"/{id}"})
    @Operation(summary = "Buscar uma conta por identificador")
    public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new Conta(id, "test"));
    }

    @PostMapping
    @Operation(summary = "Criar uma conta")
    public ResponseEntity<Conta> criarConta(@Valid @RequestBody Conta conta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Conta(1L, conta.getNome()));
    }

    
    @PatchMapping(path = {"/{id}/adicionar-saldo"})
    @Operation(summary = "Adicionar saldo em uma conta")
    public ResponseEntity<Optional<ContaDto>> adicionarSaldo(@PathVariable Long id, @Valid @RequestBody ContaDto contaSaldoAdicional) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping(path = {"/{id}"})
    @Operation(summary = "Remover uma conta por identificador")
    public ResponseEntity<Optional<Conta>> removerConta(@PathVariable Long id) {
        return ResponseEntity.accepted().body(null);
    }
}
