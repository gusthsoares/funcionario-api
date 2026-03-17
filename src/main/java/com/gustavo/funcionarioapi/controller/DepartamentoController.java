package com.gustavo.funcionarioapi.controller;

import com.gustavo.funcionarioapi.dto.DepartamentoRequest;
import com.gustavo.funcionarioapi.dto.DepartamentoResponse;
import com.gustavo.funcionarioapi.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoService service;

    public DepartamentoController(DepartamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DepartamentoResponse> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public DepartamentoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
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