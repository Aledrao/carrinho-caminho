package br.com.asas.carrinhoCaminho.service;

import br.com.asas.carrinhoCaminho.exception.DepartamentoException;
import br.com.asas.carrinhoCaminho.model.Departamento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DepartamentoService {

    public Departamento salvaOuAtualiza(Departamento departamento) throws DepartamentoException;
    public List<Departamento> listaDepartamentos() throws DepartamentoException;
    Optional<Departamento> buscaPorCodigo(Integer codigo) throws DepartamentoException;
    void excluir(Integer codigo) throws DepartamentoException;
}
