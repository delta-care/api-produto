package xyz.deltacare.produto.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.deltacare.produto.builder.ProdutoDtoBuilder;
import xyz.deltacare.produto.dto.ProdutoDto;
import xyz.deltacare.produto.service.ProdutoServiceSpa;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    @Mock private ProdutoServiceSpa service;
    @InjectMocks private ProdutoController controller;

    private MockMvc mockMvc;
    private Pageable pageable;
    private ObjectMapper objectMapper;
    private ProdutoDto produtoDtoEnviado;
    private List<ProdutoDto> produtoDtoRetornado;
    private final String PRODUTO_API_URI = "/api/v1/produtos";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        pageable = PageRequest.of(0, 10);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        produtoDtoEnviado = ProdutoDtoBuilder.builder().build().buildProdutoDto();
        produtoDtoRetornado = Collections.singletonList(ProdutoDtoBuilder.builder().id("637832").build().buildProdutoDto());
    }

    @Test
    @DisplayName("Deve tentar criar produto e retornar 201 com dados iguais")
    void criarProdutoTest() throws Exception {

        // given | cenário
        String json = new ObjectMapper().writeValueAsString(produtoDtoEnviado);

        // when | execução
        when(service.criar(produtoDtoEnviado)).thenReturn(produtoDtoRetornado.get(0));
        MockHttpServletRequestBuilder request = post(PRODUTO_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        ResultActions perform = mockMvc.perform(request);

        // then | verificação
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
        assertThat(retornoItemParaString(perform.andReturn()))
                .isEqualTo(itemParaStringIso(produtoDtoRetornado.get(0)));

    }

    @Test
    @DisplayName("Deve tentar pesquisar produto por codigo e retornar 200 com dados iguais")
    void pesquisarProdutoPorCodigoTest() throws Exception {

        // given | cenário
        String codigoEnviado = produtoDtoRetornado.get(0).getId();

        // when | execução
        when(service.pesquisar(pageable, codigoEnviado, "")).thenReturn(produtoDtoRetornado);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PRODUTO_API_URI + "/?codigo=" + codigoEnviado)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = mockMvc.perform(request);

        // then | verificação
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        assertThat(retornoListaParaString(perform.andReturn()))
                .isEqualTo(listaParaStringIso(produtoDtoRetornado));
    }

    @Test
    @DisplayName("Deve tentar pesquisar produto por nome e retornar 200 com dados iguais")
    void pesquisarProdutoPorNomeTest() throws Exception {

        // given | cenário
        String nomeEnviado = produtoDtoRetornado.get(0).getPlano().split(" ")[0];

        // when | execução
        when(service.pesquisar(pageable, "", nomeEnviado)).thenReturn(produtoDtoRetornado);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PRODUTO_API_URI + "/?nome=" + nomeEnviado)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions perform = mockMvc.perform(request);

        // then | verificação
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        assertThat(retornoListaParaString(perform.andReturn()))
                .isEqualTo(listaParaStringIso(produtoDtoRetornado));
    }

    @Test
    @DisplayName("Deve tentar atualizar produto com sucesso e retornar 200 com dados iguais")
    void atualizarProdutoTest() throws Exception {

        // given | cenário
        String json = new ObjectMapper().writeValueAsString(produtoDtoRetornado.get(0));

        // when | execução
        when(service.atualizar(produtoDtoRetornado.get(0))).thenReturn(produtoDtoRetornado.get(0));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(PRODUTO_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        ResultActions perform = mockMvc.perform(request);

        // then | verificação
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        assertThat(retornoItemParaString(perform.andReturn()))
                .isEqualTo(itemParaStringIso(produtoDtoRetornado.get(0)));
    }

    private String retornoListaParaString(MvcResult retorno) throws UnsupportedEncodingException, JsonProcessingException {
        ProdutoDto[] response = objectMapper.readValue(retorno.getResponse().getContentAsString(), ProdutoDto[].class);
        return Arrays.asList(response).toString();
    }

    private String retornoItemParaString(MvcResult retorno) throws UnsupportedEncodingException, JsonProcessingException {
        ProdutoDto response = objectMapper.readValue(retorno.getResponse().getContentAsString(), ProdutoDto.class);
        return response.toString();
    }

    private String listaParaStringIso(List<ProdutoDto> lista) {
        return new String(lista.toString().getBytes(), StandardCharsets.ISO_8859_1);
    }

    private String itemParaStringIso(ProdutoDto item) {
        return new String(item.toString().getBytes(), StandardCharsets.ISO_8859_1);
    }
}
