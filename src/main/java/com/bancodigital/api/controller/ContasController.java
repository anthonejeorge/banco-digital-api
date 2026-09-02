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
import com.bancodigital.api.service.ContasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/contas")
@Tag(name = SwaggerConfig.CONTAS_TAG, description = "Endpoints para gerenciamento de contas")
public class ContasController {

    private ContasService contasService;

    @GetMapping
    @Operation(summary = "Buscar todas as contas")
    public ResponseEntity<List<Conta>> buscarContas() {
        return ResponseEntity.ok(contasService.buscarContas());
    }

    @GetMapping(path = {"/{id}"})
    @Operation(summary = "Buscar uma conta por identificador")
    public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
        Optional<Conta> conta = contasService.buscarContaPorId(id);
        return conta.isPresent() ? ResponseEntity.ok(conta.get()) : ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Criar uma conta")
    public ResponseEntity<Conta> criarConta(@Valid @RequestBody Conta dadosConta) throws Exception {
        Conta conta = contasService.criarConta(dadosConta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    
    @PatchMapping(path = {"/{id}/adicionar-saldo"})
    @Operation(summary = "Adicionar saldo em uma conta")
    public ResponseEntity<Optional<ContaDto>> adicionarSaldo(@PathVariable Long id, @Valid @RequestBody ContaDto contaSaldoAdicional) {
        contasService.adicionarSaldo(id, contaSaldoAdicional);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = {"/{id}"})
    @Operation(summary = "Remover uma conta por identificador")
    public ResponseEntity<Optional<Conta>> removerConta(@PathVariable Long id) {
        contasService.removerConta(id);
        return ResponseEntity.ok().build();
    }
}
