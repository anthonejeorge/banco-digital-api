package com.bancodigital.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodigital.api.config.SwaggerConfig;
import com.bancodigital.api.dto.TransferirDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/transferir")
@Tag(name = SwaggerConfig.TRANSFERENCIAS_TAG, description = "Endpoint para transferência entre contas")
public class TransferenciasController {

    @PostMapping
    @Operation(summary = "Transferir saldo entre contas")
    public ResponseEntity<?> transferirEntreContas(@Valid @RequestBody TransferirDto transferenciaEntreContas) {
        return ResponseEntity.ok().build();
    }
    
}
