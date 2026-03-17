package com.gustavo.funcionarioapi.repository;

import com.gustavo.funcionarioapi.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
