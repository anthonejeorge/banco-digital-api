package com.bancodigital.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bancodigital.api.dto.ContaDto;
import com.bancodigital.api.model.Conta;
import com.bancodigital.api.service.ContasService;

@Service
public class ContasServiceImpl implements ContasService {

    @Override
    public List<Conta> buscarContas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Conta> buscarContaPorId(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Conta criarConta(Conta conta) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void adicionarSaldo(Long id, ContaDto contaSaldoAdicional) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removerConta(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
