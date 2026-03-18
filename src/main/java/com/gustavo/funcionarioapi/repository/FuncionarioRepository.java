package com.gustavo.funcionarioapi.repository;

import com.gustavo.funcionarioapi.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByDepartamentoId(Long departamentoId);

    Page<Funcionario> findByDepartamentoId(Long departamentoId, Pageable pageable);
}