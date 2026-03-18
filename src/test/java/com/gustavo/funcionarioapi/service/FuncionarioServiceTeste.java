package com.gustavo.funcionarioapi.service;

import com.gustavo.funcionarioapi.dto.FuncionarioRequest;
import com.gustavo.funcionarioapi.dto.FuncionarioResponse;
import com.gustavo.funcionarioapi.model.Departamento;
import com.gustavo.funcionarioapi.model.Funcionario;
import com.gustavo.funcionarioapi.repository.DepartamentoRepository;
import com.gustavo.funcionarioapi.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FuncionarioServiceTest {

    private FuncionarioRepository funcionarioRepository;
    private DepartamentoRepository departamentoRepository;
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        funcionarioRepository = mock(FuncionarioRepository.class);
        departamentoRepository = mock(DepartamentoRepository.class);
        funcionarioService = new FuncionarioService(funcionarioRepository, departamentoRepository);
    }

    @Test
    @DisplayName("Deve criar funcionário com departamento e calcular salário líquido")
    void deveCriarFuncionario() {
        // arrange
        FuncionarioRequest request = new FuncionarioRequest();
        request.setNome("João");
        request.setIdade(30);
        request.setSalario(1000.0);
        request.setDepartamentoId(1L);

        Departamento dep = new Departamento("TI");
        // gambiarra simples: setar id via reflection ou construtor; aqui vamos simular que já vem do repo
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(dep));

        Funcionario salvo = new Funcionario("João", 30, 1000.0);
        salvo.setDepartamento(dep);
        // simula que o banco gerou id 10
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(salvo);

        // act
        FuncionarioResponse response = funcionarioService.criar(request);

        // assert
        // verifica que salvou um funcionário com os dados certos
        ArgumentCaptor<Funcionario> captor = ArgumentCaptor.forClass(Funcionario.class);
        verify(funcionarioRepository).save(captor.capture());
        Funcionario enviado = captor.getValue();

        assertThat(enviado.getNome()).isEqualTo("João");
        assertThat(enviado.getIdade()).isEqualTo(30);
        assertThat(enviado.getSalario()).isEqualTo(1000.0);
        assertThat(enviado.getDepartamento()).isEqualTo(dep);

        // valida resposta
        assertThat(response.getNome()).isEqualTo("João");
        assertThat(response.getSalario()).isEqualTo(1000.0);
        assertThat(response.getSalarioLiquido()).isEqualTo(900.0); // 10% desconto
        assertThat(response.getDepartamentoNome()).isEqualTo("TI");
    }
}