package com.bancodigital.api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TransferirDto {

    @NotNull(message = "O identificador da conta remetente não pode ser nulo")
    @NotBlank(message = "O identificador da conta remetente não pode ser vazio")
    private Long contaRemetenteId;

    @NotNull(message = "O identificador da conta favorecida não pode ser nulo")
    @NotBlank(message = "O identificador da conta favorecida não pode ser vazio")
    private Long contaFavorecidaId;


    @Schema(description = "Valor em reais (R$)", example = "6.95")
    @Positive(message="O valor para adicionar ao saldo não pode ser menor ou igual a 0 (zero)")
	@Digits(integer = 10, fraction = 6)
    private BigDecimal saldoATransferir;
}
