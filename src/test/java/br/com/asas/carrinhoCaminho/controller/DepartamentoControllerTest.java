package br.com.asas.carrinhoCaminho.controller;

import br.com.asas.carrinhoCaminho.model.Departamento;
import br.com.asas.carrinhoCaminho.service.serviceImpl.DepartamentoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andDo(print())
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
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"codigo\" : \"1\", \"nome\" : \"Celulares\" }"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigo", is(departamentoNovo.getCodigo())))
            .andExpect(jsonPath("$.nome", is(departamentoNovo.getNome())));
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
            .andDo(print())
            .andExpect(jsonPath("$[0].codigo", is(departamentos.get(0).getCodigo())))
            .andExpect(jsonPath("$[0].nome", is(departamentos.get(0).getNome())))
            .andExpect(jsonPath("$[1].codigo", is(departamentos.get(1).getCodigo())))
            .andExpect(jsonPath("$[1].nome", is(departamentos.get(1).getNome())));
    }

    @Test
    void deveriaBuscaPorCodigo_sucesso() throws Exception {
        Departamento departamento = new Departamento();
        departamento.setCodigo(3);
        departamento.setNome("Açougue");

        Optional<Departamento> optionalDepartamento = Optional.of(departamento);

        when(departamentoService.buscaPorCodigo(3)).thenReturn(optionalDepartamento);

        mockMvc.perform(get(DEPARTAMENTO + "/por-codigo/3")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.codigo", is(optionalDepartamento.get().getCodigo())))
            .andExpect(jsonPath("$.nome", is(optionalDepartamento.get().getNome())));
    }

    @Test
    void deveriaBuscarPorNome_Sucesso()throws Exception {
        Departamento departamentoUm = new Departamento();
        departamentoUm.setCodigo(1);
        departamentoUm.setNome("abcdef");

        Departamento departamentoDois = new Departamento();
        departamentoDois.setCodigo(2);
        departamentoDois.setNome("abc123");

        List<Departamento> departamentos = new ArrayList<>(Arrays.asList(departamentoUm, departamentoDois));

        when(departamentoService.buscaPorNome("abc")).thenReturn(departamentos);

        mockMvc.perform(get(DEPARTAMENTO + "/busca-por-nome/abc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$[0].codigo", is(departamentos.get(0).getCodigo())))
            .andExpect(jsonPath("$[0].nome", is(departamentos.get(0).getNome())))
            .andExpect(jsonPath("$[1].codigo", is(departamentos.get(1).getCodigo())))
            .andExpect(jsonPath("$[1].nome", is(departamentos.get(1).getNome())))
        ;
    }
}