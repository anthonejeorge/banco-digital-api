package com.bancodigital.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancodigital.api.model.Auditoria;


@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long>{
}
