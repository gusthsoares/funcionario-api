package com.gustavo.funcionarioapi.dto;

public class DepartamentoResponse {

    private Long id;
    private String nome;
    private Integer quantidadeFuncionarios;

    public DepartamentoResponse(Long id, String nome, Integer quantidadeFuncionarios) {
        this.id = id;
        this.nome = nome;
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getQuantidadeFuncionarios() {
        return quantidadeFuncionarios;
    }
}