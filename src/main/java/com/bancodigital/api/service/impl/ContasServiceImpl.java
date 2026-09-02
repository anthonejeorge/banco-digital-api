package com.bancodigital.api.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bancodigital.api.dto.ContaDto;
import com.bancodigital.api.model.Conta;
import com.bancodigital.api.repository.ContasRepository;
import com.bancodigital.api.service.ContasService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class ContasServiceImpl implements ContasService {

    private final ContasRepository contasRepository;

    @Override
    public List<Conta> buscarContas() {
        return contasRepository.findAll();
    }

    @Override
    public Optional<Conta> buscarContaPorId(Long id) {
        return contasRepository.findById(id);
    }

    @Override
    public Conta criarConta(Conta conta) throws Exception {
        return contasRepository.save(conta);
    }

    @Override
    public void adicionarSaldo(Long id, ContaDto contaSaldoAdicional) {
        Conta conta = contasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
        
        conta.setSaldo(conta.getSaldo().add(contaSaldoAdicional.getValor()));
        contasRepository.save(conta);
    }

    @Override
    public void removerConta(Long id) {
        Conta conta = contasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada para remoção."));

        if (conta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("A conta só pode ser removida se o saldo for zero.");
        }

        contasRepository.deleteById(id);
    }
}
