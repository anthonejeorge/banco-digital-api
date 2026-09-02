package com.bancodigital.api.unit.service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bancodigital.api.model.Auditoria;
import com.bancodigital.api.repository.AuditoriaRepository;
import com.bancodigital.api.repository.ContasRepository;
import com.bancodigital.api.service.impl.TransferenciasServiceImpl;

@ExtendWith(MockitoExtension.class)
class TransferenciasServiceTest {

    @Mock
    private ContasRepository contasRepository;

    @Mock
    private AuditoriaRepository auditoriaRepository;

    @InjectMocks
    private TransferenciasServiceImpl transferenciasService;

    @Test
    void deveEfetuarTransferenciaComSucesso() {
        Long remetenteId = 1L;
        Long favorecidoId = 2L;
        BigDecimal valor = new BigDecimal("100.00");

        when(contasRepository.realizaDebito(remetenteId, valor)).thenReturn(1);
        when(contasRepository.realizaCredito(favorecidoId, valor)).thenReturn(1);

        boolean resultado = transferenciasService.efetuarTransferencia(remetenteId, favorecidoId, valor);

        assertTrue(resultado);
        verify(contasRepository, times(1)).realizaDebito(remetenteId, valor);
        verify(contasRepository, times(1)).realizaCredito(favorecidoId, valor);
        verify(auditoriaRepository, times(1)).save(any(Auditoria.class));
    }

    @Test
    void naoDevePermitirTransferenciaParaMesmaConta() {
        Long contaId = 1L;
        BigDecimal valor = new BigDecimal("50.00");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> transferenciasService.efetuarTransferencia(contaId, contaId, valor)
        );

        assertEquals("Os identificadores das contas não podem ser iguais", exception.getMessage());
        
        verifyNoInteractions(contasRepository);
        verifyNoInteractions(auditoriaRepository);
    }

    @Test
    void deveLancarExcecaoQuandoDebitoFalharPorSaldoInsuficienteOuContaInvalida() {
        Long remetenteId = 1L;
        Long favorecidoId = 2L;
        BigDecimal valor = new BigDecimal("500.00");

        when(contasRepository.realizaDebito(remetenteId, valor)).thenReturn(0);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> transferenciasService.efetuarTransferencia(remetenteId, favorecidoId, valor)
        );

        assertEquals("A conta não tem saldo suficiente ou não existe.", exception.getMessage());
        
        verify(contasRepository, times(1)).realizaDebito(remetenteId, valor);
        verify(contasRepository, never()).realizaCredito(anyLong(), any());
        verifyNoInteractions(auditoriaRepository);
    }

    @Test
    void deveLancarExcecaoQuandoContaFavorecidaNaoExistir() {
        Long remetenteId = 1L;
        Long favorecidoId = 99L;
        BigDecimal valor = new BigDecimal("50.00");

        when(contasRepository.realizaDebito(remetenteId, valor)).thenReturn(1);
        when(contasRepository.realizaCredito(favorecidoId, valor)).thenReturn(0);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> transferenciasService.efetuarTransferencia(remetenteId, favorecidoId, valor)
        );

        assertEquals("A conta favorecida não existe.", exception.getMessage());
        
        verify(contasRepository, times(1)).realizaDebito(remetenteId, valor);
        verify(contasRepository, times(1)).realizaCredito(favorecidoId, valor);
        verifyNoInteractions(auditoriaRepository);
    }
}
