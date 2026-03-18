package com.gustavo.funcionarioapi.service;

import com.gustavo.funcionarioapi.dto.DepartamentoRequest;
import com.gustavo.funcionarioapi.dto.DepartamentoResponse;
import com.gustavo.funcionarioapi.exception.RegistroNaoEncontradoException;
import com.gustavo.funcionarioapi.model.Departamento;
import com.gustavo.funcionarioapi.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    private final DepartamentoRepository repository;

    public DepartamentoService(DepartamentoRepository repository) {
        this.repository = repository;
    }

    public List<DepartamentoResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DepartamentoResponse buscarPorId(Long id) {
        Departamento departamento = repository.findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Departamento não encontrado com id: " + id));
        return toResponse(departamento);
    }

    public DepartamentoResponse criar(DepartamentoRequest request) {
        Departamento departamento = new Departamento(request.getNome());
        Departamento salvo = repository.save(departamento);
        return toResponse(salvo);
    }

    public DepartamentoResponse atualizar(Long id, DepartamentoRequest request) {
        Departamento existente = repository.findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Departamento não encontrado com id: " + id));
        existente.setNome(request.getNome());
        Departamento atualizado = repository.save(existente);
        return toResponse(atualizado);
    }

    public void deletar(Long id) {
        Departamento existente = repository.findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "Departamento não encontrado com id: " + id));
        repository.delete(existente);
    }

    private DepartamentoResponse toResponse(Departamento d) {
        int quantidade = d.getFuncionarios() == null ? 0 : d.getFuncionarios().size();
        return new DepartamentoResponse(d.getId(), d.getNome(), quantidade);
    }
}