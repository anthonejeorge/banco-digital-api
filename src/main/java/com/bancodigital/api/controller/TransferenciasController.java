package com.bancodigital.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodigital.api.config.SwaggerConfig;
import com.bancodigital.api.dto.TransferirDto;
import com.bancodigital.api.service.TransferenciasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/transferir")
@Tag(name = SwaggerConfig.TRANSFERENCIAS_TAG, description = "Endpoint para transferência entre contas")
public class TransferenciasController {

    private final TransferenciasService service;

    @PostMapping
    @Operation(summary = "Transferir saldo entre contas")
    public ResponseEntity<?> transferirEntreContas(@Valid @RequestBody TransferirDto transferenciaEntreContas) {
        service.efetuarTransferencia(transferenciaEntreContas.getContaRemetenteId(), transferenciaEntreContas.getContaFavorecidaId(), transferenciaEntreContas.getValor());
        return ResponseEntity.ok(Map.of("mensagem", "Transferência concluída com sucesso!"));
    }
    
}
