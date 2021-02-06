package br.com.asas.carrinhoCaminho.controller;

import br.com.asas.carrinhoCaminho.exception.DepartamentoException;
import br.com.asas.carrinhoCaminho.service.serviceImpl.DepartamentoServiceImpl;
import br.com.asas.carrinhoCaminho.model.Departamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
@RequestMapping("departamento")
public class DepartamentoController {

    private static final Logger LOGGER = Logger.getLogger(DepartamentoController.class.getName());

    @Autowired
    private DepartamentoServiceImpl departamentoService;

    @PostMapping("salvar")
    public ResponseEntity<?> salvar(@RequestBody Departamento departamento) {
        try {
            LOGGER.info("Incluindo novo departamento : " + departamento.getNome());
            Departamento departamentoSalvo = departamentoService.salvaOuAtualiza(departamento);
            return ResponseEntity.ok(departamento);
        } catch (DepartamentoException de) {
            return ResponseEntity.ok("Problema com banco de dados");
        }
    }

    @PostMapping("atualizar")
    public ResponseEntity<?> atualizar(@RequestBody Departamento departamento) {
        try {
            LOGGER.info("Atualizando  departamento : " + departamento.getNome());
            Departamento departamentoSalvo = departamentoService.salvaOuAtualiza(departamento);
            return ResponseEntity.ok(departamento);
        } catch (DepartamentoException de) {
            return ResponseEntity.ok("Problema com banco de dados");
        }
    }
    
    @GetMapping("todos")
    public  ResponseEntity<?> listaDepartamentos() {
        try {
            LOGGER.info("Buscando todos os departamentos.");
            List<Departamento> departamentos = departamentoService.listaDepartamentos();
            return ResponseEntity.ok(departamentos);
        } catch (DepartamentoException de) {
            return ResponseEntity.ok("Problema com banco de dados");
        }
    }

    private Departamento buscaPorId(Integer codigo) throws DepartamentoException {
        Optional<Departamento> departamento = departamentoService.buscaPorCodigo(codigo);
        if(departamento.isPresent()) {
            return departamento.get();
        }
        return null;
    }
}
