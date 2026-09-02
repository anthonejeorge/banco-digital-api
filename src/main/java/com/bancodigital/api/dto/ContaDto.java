package com.bancodigital.api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ContaDto {

    @Schema(description = "Valor em reais (R$)", example = "6.95")
    @Positive(message="O valor para adicionar ao saldo não pode ser menor ou igual a 0 (zero)")
	@Digits(integer = 10, fraction = 6)
    private BigDecimal saldo;
    
}
