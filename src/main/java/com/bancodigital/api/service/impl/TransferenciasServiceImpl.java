package com.bancodigital.api.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.bancodigital.api.service.TransferenciasService;

@Service
public class TransferenciasServiceImpl implements TransferenciasService {

    @Override
    public boolean efetuarTransferencia(Long remetenteId, Long favorecidoId, BigDecimal valor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
