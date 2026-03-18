package com.gustavo.funcionarioapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavo.funcionarioapi.dto.FuncionarioRequest;
import com.gustavo.funcionarioapi.dto.FuncionarioResponse;
import com.gustavo.funcionarioapi.service.FuncionarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuncionarioController.class)
class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FuncionarioService funcionarioService;

    @Test
    @DisplayName("GET /funcionarios deve retornar pagina de funcionarios")
    void listarFuncionariosPaginado() throws Exception {
        FuncionarioResponse f1 =
                new FuncionarioResponse(1L, "João", 30, 1000.0, 900.0, "TI");

        var page = new PageImpl<>(List.of(f1), PageRequest.of(0, 10), 1);

        Mockito.when(funcionarioService.listarTodos(any()))
                .thenReturn(page);

        mockMvc.perform(get("/funcionarios")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("João"))
                .andExpect(jsonPath("$.content[0].salarioLiquido").value(900.0));
    }

    @Test
    @DisplayName("POST /funcionarios deve criar funcionário e retornar 201")
    void criarFuncionario() throws Exception {
        FuncionarioRequest request = new FuncionarioRequest();
        request.setNome("Maria");
        request.setIdade(25);
        request.setSalario(2000.0);
        request.setDepartamentoId(1L);

        FuncionarioResponse response =
                new FuncionarioResponse(10L, "Maria", 25, 2000.0, 1800.0, "TI");

        Mockito.when(funcionarioService.criar(any(FuncionarioRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.salarioLiquido").value(1800.0));
    }
}