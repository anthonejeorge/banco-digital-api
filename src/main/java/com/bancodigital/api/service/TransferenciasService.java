package com.bancodigital.api.service;

import java.math.BigDecimal;


public interface TransferenciasService {
    public boolean efetuarTransferencia(Long remetenteId, Long favorecidoId, BigDecimal valor);
}