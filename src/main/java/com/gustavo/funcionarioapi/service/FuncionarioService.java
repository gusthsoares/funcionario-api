package com.gustavo.funcionarioapi.service;

import com.gustavo.funcionarioapi.dto.FuncionarioRequest;
import com.gustavo.funcionarioapi.dto.FuncionarioResponse;
import com.gustavo.funcionarioapi.exception.RegistroNaoEncontradoException;
import com.gustavo.funcionarioapi.model.Departamento;
import com.gustavo.funcionarioapi.model.Funcionario;
import com.gustavo.funcionarioapi.repository.DepartamentoRepository;
import com.gustavo.funcionarioapi.repository.FuncionarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final DepartamentoRepository departamentoRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              DepartamentoRepository departamentoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public Page<FuncionarioResponse> listarTodos(Pageable pageable) {
        return funcionarioRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public Page<FuncionarioResponse> listarPorDepartamento(Long departamentoId, Pageable pageable) {
        buscarDepartamento(departamentoId);
        return funcionarioRepository.findByDepartamentoId(departamentoId, pageable)
                .map(this::toResponse);
    }

    public FuncionarioResponse buscarPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Funcionário não encontrado com id: " + id));
        return toResponse(funcionario);
    }

    public FuncionarioResponse criar(FuncionarioRequest request) {
        Departamento departamento = buscarDepartamento(request.getDepartamentoId());

        Funcionario funcionario = new Funcionario(
                request.getNome(),
                request.getIdade(),
                request.getSalario()
        );
        funcionario.setDepartamento(departamento);

        Funcionario salvo = funcionarioRepository.save(funcionario);
        return toResponse(salvo);
    }

    public FuncionarioResponse atualizar(Long id, FuncionarioRequest request) {
        Funcionario existente = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Funcionário não encontrado com id: " + id));

        Departamento departamento = buscarDepartamento(request.getDepartamentoId());

        existente.setNome(request.getNome());
        existente.setIdade(request.getIdade());
        existente.setSalario(request.getSalario());
        existente.setDepartamento(departamento);

        Funcionario atualizado = funcionarioRepository.save(existente);
        return toResponse(atualizado);
    }

    public void deletar(Long id) {
        Funcionario existente = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Funcionário não encontrado com id: " + id));
        funcionarioRepository.delete(existente);
    }

    private Departamento buscarDepartamento(Long departamentoId) {
        return departamentoRepository.findById(departamentoId)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Departamento não encontrado com id: " + departamentoId));
    }

    private FuncionarioResponse toResponse(Funcionario f) {
        Double salario = f.getSalario();
        Double salarioLiquido = salario == null ? null : salario * 0.9;
        String departamentoNome = f.getDepartamento() != null
                ? f.getDepartamento().getNome()
                : null;

        return new FuncionarioResponse(
                f.getId(),
                f.getNome(),
                f.getIdade(),
                salario,
                salarioLiquido,
                departamentoNome
        );
    }
}