package com.bancodigital.api.service;

import java.util.List;
import java.util.Optional;

import com.bancodigital.api.dto.ContaDto;
import com.bancodigital.api.model.Conta;


public interface ContasService {
    public List<Conta> buscarContas();

    public Optional<Conta> buscarContaPorId(Long id);

    public Conta criarConta(Conta conta) throws Exception;
    
    public void adicionarSaldo(Long id, ContaDto contaSaldoAdicional);

    public void removerConta(Long id);
}
