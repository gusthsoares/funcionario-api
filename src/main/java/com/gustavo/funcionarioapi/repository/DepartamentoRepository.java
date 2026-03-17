package com.gustavo.funcionarioapi.repository;

import com.gustavo.funcionarioapi.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}