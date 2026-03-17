package com.gustavo.funcionarioapi.dto;

public class FuncionarioResponse {

    private Long id;
    private String nome;
    private Integer idade;
    private Double salario;
    private Double salarioLiquido;
    private String departamentoNome;

    public FuncionarioResponse(Long id, String nome, Integer idade,
                               Double salario, Double salarioLiquido,
                               String departamentoNome) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.salario = salario;
        this.salarioLiquido = salarioLiquido;
        this.departamentoNome = departamentoNome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public Double getSalario() {
        return salario;
    }

    public Double getSalarioLiquido() {
        return salarioLiquido;
    }

    public String getDepartamentoNome() {
        return departamentoNome;
    }
}