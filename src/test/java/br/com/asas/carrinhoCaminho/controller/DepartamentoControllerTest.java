package br.com.asas.carrinhoCaminho.controller;

import br.com.asas.carrinhoCaminho.model.Departamento;
import br.com.asas.carrinhoCaminho.repository.DepartamentoRepository;
import br.com.asas.carrinhoCaminho.service.DepartamentoService;
import br.com.asas.carrinhoCaminho.service.serviceImpl.DepartamentoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CharMatcher;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest
//@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DepartamentoControllerTest {

    public static final String DEPARTAMENTO = "/departamento";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private DepartamentoServiceImpl departamentoService;

    @InjectMocks
    private DepartamentoController departamentoController;

//    @Mock
//    private DepartamentoService deptoMockService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(departamentoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void deveriaSalvar_sucesso() throws Exception {
        Departamento departamento = new Departamento();
        departamento.setCodigo(1);
        departamento.setNome("Padaria");

        when(departamentoService.salvaOuAtualiza(departamento)).thenReturn(departamento);

        mockMvc.perform(post(DEPARTAMENTO + "/salvar")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is(departamento.getCodigo())))
                .andExpect(jsonPath("$.nome", is(departamento.getNome())));
    }

    @Test
    void deveriaAtualizar_sucesso() throws Exception {
        Departamento departamentoAntigo = new Departamento();
        departamentoAntigo.setCodigo(1);
        departamentoAntigo.setNome("Telefonia");

        Departamento departamentoNovo = new Departamento();
        departamentoNovo.setCodigo(1);
        departamentoNovo.setNome("Celulares");

        when(departamentoService.salvaOuAtualiza(departamentoNovo)).thenReturn(departamentoNovo);

        mockMvc.perform(post(DEPARTAMENTO + "/atualizar")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void deveriaListaDepartamentos_sucesso() throws Exception {
        Departamento departamentoUm = new Departamento();
        departamentoUm.setCodigo(1);
        departamentoUm.setNome("Eletrônicos");

        Departamento departamentoDois = new Departamento();
        departamentoDois.setCodigo(2);
        departamentoDois.setNome("Pets");

        List<Departamento> departamentos = new ArrayList<>(Arrays.asList(departamentoUm, departamentoDois));

        when(departamentoService.listaDepartamentos()).thenReturn(departamentos);

        mockMvc.perform(get(DEPARTAMENTO + "/todos")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].codigo", is(departamentos.get(0).getCodigo())))
            .andExpect(jsonPath("$[0].nome", is(departamentos.get(0).getNome())))
            .andExpect(jsonPath("$[1].codigo", is(departamentos.get(1).getCodigo())))
            .andExpect(jsonPath("$[1].nome", is(departamentos.get(1).getNome())));
    }

    @Test
    void buscaPorCodigo() {
    }
}