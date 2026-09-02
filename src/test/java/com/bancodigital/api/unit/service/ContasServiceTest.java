package com.bancodigital.api.unit.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bancodigital.api.dto.ContaDto;
import com.bancodigital.api.model.Conta;
import com.bancodigital.api.repository.ContasRepository;
import com.bancodigital.api.service.impl.ContasServiceImpl;

@ExtendWith(MockitoExtension.class)
class ContasServiceTest {

    @Mock
    private ContasRepository contasRepository;

    @InjectMocks
    private ContasServiceImpl contasService;

    @Test
    void deveRetornarListaDeContasComSucesso() {
        when(contasRepository.findAll()).thenReturn(listaContas());

        List<Conta> resultado = contasService.buscarContas();

        assertNotNull(resultado);
        assertEquals(4, resultado.size());
        verify(contasRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarContaPorIdQuandoExistir() {
        Long id = 1L;
        when(contasRepository.findById(id)).thenReturn(Optional.of(listaContas().get(0)));

        Optional<Conta> resultado = contasService.buscarContaPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(listaContas().get(0), resultado.get());
        verify(contasRepository, times(1)).findById(id);
    }

    @Test
    void deveCriarContaComSucesso() throws Exception {
        Conta conta = listaContas().get(0);
        when(contasRepository.save(conta)).thenReturn(conta);

        Conta resultado = contasService.criarConta(conta);

        assertNotNull(resultado);
        verify(contasRepository, times(1)).save(conta);
    }

    @Test
    void deveAdicionarSaldoComSucesso() {
        Long id = 1L;
        Conta contaExistente = listaContas().get(0);
        contaExistente.setSaldo(new BigDecimal("100.00"));

        ContaDto dto = new ContaDto(new BigDecimal("50.00"));

        when(contasRepository.findById(id)).thenReturn(Optional.of(contaExistente));
        when(contasRepository.save(any(Conta.class))).thenReturn(contaExistente);

        contasService.adicionarSaldo(id, dto);

        assertEquals(new BigDecimal("150.00"), contaExistente.getSaldo());
        verify(contasRepository, times(1)).findById(id);
        verify(contasRepository, times(1)).save(contaExistente);
    }

    @Test
    void deveLancarExcecaoAoAdicionarSaldoEmContaInexistente() {
        Long id = 99L;
        ContaDto dto = new ContaDto(new BigDecimal("50.00"));

        when(contasRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> contasService.adicionarSaldo(id, dto)
        );

        assertEquals("Conta não encontrada.", exception.getMessage());
        verify(contasRepository, times(1)).findById(id);
        verify(contasRepository, never()).save(any());
    }

    @Test
    void deveRemoverContaComSucesso() {
        Long id = 1L;
        Conta conta = new Conta();
        conta.setSaldo(BigDecimal.ZERO);

        when(contasRepository.findById(id)).thenReturn(Optional.of(conta));
        doNothing().when(contasRepository).deleteById(id);

        contasService.removerConta(id);

        verify(contasRepository, times(1)).findById(id);
        verify(contasRepository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoRemoverContaComSaldoDiferenteDeZero() {
        Long id = 1L;
        Conta conta = new Conta();
        conta.setSaldo(new BigDecimal("50.00"));

        when(contasRepository.findById(id)).thenReturn(Optional.of(conta));

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> contasService.removerConta(id)
        );

        assertEquals("A conta só pode ser removida se o saldo for zero.", exception.getMessage());
        verify(contasRepository, times(1)).findById(id);
        verify(contasRepository, never()).deleteById(anyLong());
    }

    @Test
    void deveLancarExcecaoAoRemoverContaInexistente() {
        Long id = 99L;
        when(contasRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> contasService.removerConta(id)
        );

        assertEquals("Conta não encontrada para remoção.", exception.getMessage());
        verify(contasRepository, times(1)).findById(id);
        verify(contasRepository, never()).deleteById(anyLong());
    }

    private List<Conta> listaContas() {
        return List.of(
            Conta.builder().id(1L).nome("TESTE1").saldo(BigDecimal.valueOf(10.0)).build(),
            Conta.builder().id(2L).nome("TESTE2").saldo(BigDecimal.valueOf(20.0)).build(),
            Conta.builder().id(3L).nome("TESTE3").saldo(BigDecimal.valueOf(30.0)).build(),
            Conta.builder().id(4L).nome("TESTE4").saldo(BigDecimal.valueOf(40.0)).build()
        );
    } 
}
