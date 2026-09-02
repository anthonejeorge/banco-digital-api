package com.bancodigital.api.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancodigital.api.model.Conta;


@Repository
public interface ContasRepository extends JpaRepository<Conta, Long>{

    @Modifying
    @Query("UPDATE Conta c SET c.saldo = c.saldo - :valorATransferir WHERE c.id = :id AND c.saldo >= :valorATransferir")
    int realizaDebito(@Param("id") Long id, @Param("valorATransferir") BigDecimal valorATransferir);

    @Modifying
    @Query("UPDATE Conta c SET c.saldo = c.saldo + :valorAReceber WHERE c.id = :id")
    int realizaCredito(@Param("id") Long id, @Param("valorAReceber") BigDecimal valorAReceber);
}
