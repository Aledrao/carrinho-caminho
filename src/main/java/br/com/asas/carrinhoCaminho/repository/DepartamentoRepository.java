package br.com.asas.carrinhoCaminho.repository;

import br.com.asas.carrinhoCaminho.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
}
