package com.bancodigital.api.unit.controller;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.bancodigital.api.controller.TransferenciasController;
import com.bancodigital.api.dto.TransferirDto;
import com.bancodigital.api.service.TransferenciasService;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TransferenciasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransferenciasController controller;

    @MockitoBean
    private TransferenciasService service;
    
    @Autowired
    private JacksonTester<TransferirDto> jsonTransferirDto;

    @Test
    @Order(1)
    @DisplayName("Dado que a aplicação inicie corretamente, quando o objeto controller for verificado deve retornar não nulo com uma instância válida")
    public void deveRetornarNaoNuloQuandoAcessarController() {
        assertNotNull(controller);
    }

    @Test
    @Order(2)
    @DisplayName("Dado que duas contas com saldo suficiente existam, quando o usuário enviar uma transferência, deve retornar sucesso")
    public void deveRetornarSucessoQuandoExecutarTranferenciaEntreContas() throws Exception {
        TransferirDto transferencia = TransferirDto.builder()
            .contaRemetenteId(2L)
            .contaFavorecidaId(2L)
            .valor(BigDecimal.valueOf(10))
            .build();

		given(service.efetuarTransferencia(1L, 2L, BigDecimal.valueOf(10))).willReturn(true);
		
		MockHttpServletResponse response = mockMvc.perform(
                post("/transferir")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTransferirDto.write(transferencia)
                .getJson())).andReturn().getResponse();

        verify(service, times(1)).efetuarTransferencia(any(), any(), any());
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Order(3)
    @DisplayName("Dado que o usuário informe uma entrada inválida, quando a aplicação executar a requisição, deve retornar 400")
    public void deveRetornarBadRequestQuandoExecutarTranferenciaEntreContas() throws Exception {
        TransferirDto transferencia = TransferirDto.builder()
            .contaRemetenteId(null)
            .contaFavorecidaId(2L)
            .valor(BigDecimal.valueOf(10))
            .build();
		
		MockHttpServletResponse response = mockMvc.perform(
                post("/transferir")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTransferirDto.write(transferencia)
                .getJson())).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
}
