package com.bancodigital.api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancodigital.api.model.Auditoria;
import com.bancodigital.api.repository.AuditoriaRepository;
import com.bancodigital.api.repository.ContasRepository;
import com.bancodigital.api.service.TransferenciasService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransferenciasServiceImpl implements TransferenciasService {

    private final ContasRepository contasRepository;

    private final AuditoriaRepository auditoriaRepository;

    @Override
    @Transactional
    public boolean efetuarTransferencia(Long remetenteId, Long favorecidoId, BigDecimal valor) {
        if (remetenteId.equals(favorecidoId)) throw new IllegalArgumentException("Os identificadores das contas não podem ser iguais");
        
        int contaDebito = contasRepository.realizaDebito(remetenteId, valor);
        if (contaDebito == 0) {
            throw new IllegalArgumentException("A conta não tem saldo suficiente ou não existe.");
        }

        int contaCredito = contasRepository.realizaCredito(favorecidoId, valor);
        if (contaCredito == 0) {
            throw new IllegalArgumentException("A conta favorecida não existe.");
        }

        auditoriaRepository.save(
            Auditoria.builder()
                .contaRemetenteId(remetenteId)
                .contaFavorecidaId(favorecidoId)
                .valor(valor)
                .dataHoraTransacao(LocalDateTime.now())
            .build());
        return true;
    }
    
}
