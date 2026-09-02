package com.bancodigital.api.model;


import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da conta não pode ser vazio ou nulo")
    private String nome;

    @Schema(description = "Valor em reais (R$)", example = "6.95")
    @DecimalMin(value="0.0", message="O valor do saldo não pode ser menor do que 0 (zero)")
	@Digits(integer = 10, fraction = 6)
    private BigDecimal saldo;

}