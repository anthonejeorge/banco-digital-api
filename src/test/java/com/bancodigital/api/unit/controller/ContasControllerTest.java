package com.bancodigital.api.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bancodigital.api.controller.ContasController;
import com.bancodigital.api.dto.ContaDto;
import com.bancodigital.api.exception.GlobalExceptionHandler;
import com.bancodigital.api.model.Conta;
import com.bancodigital.api.service.ContasService;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Import(GlobalExceptionHandler.class)
public class ContasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContasController controller;

    @MockitoBean
    private ContasService service;

    @Autowired
    private JacksonTester<Conta> jsonConta;
    
    @Autowired
    private JacksonTester<ContaDto> jsonContaDto;
    
    @Autowired
    private JacksonTester<List<Conta>> jsonListaContas;

    @Test
    @Order(1)
    @DisplayName("Dado que a aplicação inicie corretamente, quando o objeto controller for verificado deve retornar não nulo com uma instância válida")
    public void deveRetornarNaoNuloQuandoAcessarController() {
        assertNotNull(controller);
    }

    @Test
    @Order(2)
    @DisplayName("Dado que existem contas, quando o usuário buscar contas, deve retornar uma lista de contas")
    public void deveRetornarListaContasQuandoExecutarBuscarContas() throws Exception {
		given(service.buscarContas()).willReturn(listaContas());
		
		MockHttpServletResponse response = mockMvc.perform(get("/contas")).andReturn().getResponse();

        verify(service, times(1)).buscarContas();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonListaContas.write(listaContas()).getJson());
    }

    @Test
    @Order(3)
    @DisplayName("Dado que a existe uma conta para determinado identificador, quando o usuário buscar a conta por id, deve retornar a conta")
    public void deveRetornarContaQuandoExecutarBuscarContaPorId() throws Exception {
		given(service.buscarContaPorId(1L)).willReturn(Optional.of(listaContas().get(0)));
		
		MockHttpServletResponse response = mockMvc.perform(get("/contas/1")).andReturn().getResponse();

        verify(service, times(1)).buscarContaPorId(1L);
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonConta.write(Optional.of(listaContas().get(0)).get()).getJson());
    }

    @Test
    @Order(4)
    @DisplayName("Dado que não exista uma conta para determinado identificador, quando o usuário buscar a conta por id, deve retornar 202")
    public void deveRetornarNoContentQuandoExecutarBuscarContaPorId() throws Exception {
		given(service.buscarContaPorId(1L)).willReturn(Optional.empty());
		
		MockHttpServletResponse response = mockMvc.perform(get("/contas/1")).andReturn().getResponse();

        verify(service, times(1)).buscarContaPorId(1L);
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @Order(5)
    @DisplayName("Dado que uma conta não exista, quando o usuário enviar uma conta para ser criada, deve criar e retornar a conta")
    public void deveRetornarContaQuandoExecutarCriarUmaConta() throws Exception {
		given(service.criarConta(listaContas().get(0))).willReturn(listaContas().get(0));
		
		MockHttpServletResponse response = mockMvc.perform(
                post("/contas")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConta.write(listaContas().get(0)).getJson()))
            .andReturn().getResponse();

        verify(service, times(1)).criarConta(any());
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonConta.write(listaContas().get(0)).getJson());
    }

    @Test
    @Order(6)
    @DisplayName("Dado que uma conta exista, quando o usuário adicionar saldo, deve retornar sucesso")
    public void deveRetornarSucessoQuandoExecutarAdicionarSaldo() throws Exception {
		doNothing().when(service).adicionarSaldo(any(), any());

		MockHttpServletResponse response = mockMvc.perform(
                patch("/contas/1/adicionar-saldo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContaDto.write(new ContaDto(BigDecimal.valueOf(10.0))).getJson()))
            .andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Order(7)
    @DisplayName("Dado que uma conta exista, quando o usuário remover a conta, deve retornar sucesso")
    public void deveRetornarSucessoQuandoExecutarRemoverConta() throws Exception {
		doNothing().when(service).removerConta(any());

		MockHttpServletResponse response = mockMvc.perform(
                delete("/contas/1")).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Order(8)
    @DisplayName("Dado que uma conta não exista, quando o usuário enviar uma conta inválida para ser criada, deve retornar 400")
    public void deveRetornarBadRequestQuandoExecutarCriarUmaContaComDadosInvalidos() throws Exception {
		MockHttpServletResponse response = mockMvc.perform(
                post("/contas")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConta.write(Conta.builder().nome("").build()).getJson()))
            .andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(9)
    @DisplayName("Dado que a aplicação não está saudável, quando o usuário enviar uma requisição, deve retornar 500 (unchecked exceptions)")
    public void deveRetornarInternalServerErrorQuandoExecutarQualquerAcaoNaoVerificada() throws Exception {
        given(service.criarConta(any())).willThrow(new RuntimeException("Erro simulado."));

		mockMvc.perform(
                post("/contas")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConta.write(listaContas().get(0)).getJson()))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.erro").value("Um erro ocorreu."))
                .andExpect(jsonPath("$.mensagem").value("Erro simulado."));
    }

    @Test
    @Order(10)
    @DisplayName("Dado que a aplicação não está saudável, quando o usuário enviar uma requisição, deve retornar 500 (checked exceptions)")
    public void deveRetornarInternalServerErrorQuandoExecutarQualquerAcao() throws Exception {
        given(service.criarConta(any())).willThrow(new IOException("Erro genérico."));

		mockMvc.perform(
                post("/contas")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConta.write(listaContas().get(0)).getJson()))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.erro").value("Um erro ocorreu."))
                .andExpect(jsonPath("$.mensagem").value("Erro genérico."));
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
