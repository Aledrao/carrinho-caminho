package br.com.asas.carrinhoCaminho.service.serviceImpl;

import br.com.asas.carrinhoCaminho.exception.DepartamentoException;
import br.com.asas.carrinhoCaminho.model.Departamento;
import br.com.asas.carrinhoCaminho.repository.DepartamentoRepository;
import br.com.asas.carrinhoCaminho.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Override
    public Departamento salvaOuAtualiza(Departamento departamento) throws DepartamentoException {
        try {
            Departamento departamendoSalvo = departamentoRepository.save(departamento);
            return departamendoSalvo;
        } catch (Exception e) {
            throw new DepartamentoException("Erro ao salvar ou atualizar departamento", e);
        }
    }

    @Override
    public List<Departamento> listaDepartamentos() throws DepartamentoException {
        try {
            List<Departamento> departamentos = departamentoRepository.findAll();
            return departamentos;
        } catch (Exception e) {
            throw new DepartamentoException("Erro ao buscar todos os departamento", e);
        }
    }
}
