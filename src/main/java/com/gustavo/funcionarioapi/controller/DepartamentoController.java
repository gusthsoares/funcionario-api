package com.gustavo.funcionarioapi.controller;

import com.gustavo.funcionarioapi.dto.DepartamentoRequest;
import com.gustavo.funcionarioapi.dto.DepartamentoResponse;
import com.gustavo.funcionarioapi.dto.FuncionarioResponse;
import com.gustavo.funcionarioapi.service.DepartamentoService;
import com.gustavo.funcionarioapi.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoService service;
    private final FuncionarioService funcionarioService;

    public DepartamentoController(DepartamentoService service,
                                  FuncionarioService funcionarioService) {
        this.service = service;
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public List<DepartamentoResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public DepartamentoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/{id}/funcionarios")
    public Page<FuncionarioResponse> listarFuncionarios(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return funcionarioService.listarPorDepartamento(id, pageable);
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponse> criar(@RequestBody @Valid DepartamentoRequest request) {
        DepartamentoResponse salvo = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public DepartamentoResponse atualizar(@PathVariable Long id,
                                          @RequestBody @Valid DepartamentoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}