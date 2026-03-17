package com.gustavo.funcionarioapi.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartamentoRequest {

    @NotBlank(message = "Nome do departamento é obrigatório")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}