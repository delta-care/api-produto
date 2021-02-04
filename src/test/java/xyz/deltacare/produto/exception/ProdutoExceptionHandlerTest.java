package xyz.deltacare.produto.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import xyz.deltacare.produto.builder.ProdutoDtoBuilder;
import xyz.deltacare.produto.controller.ProdutoController;
import xyz.deltacare.produto.dto.ProdutoDto;
import xyz.deltacare.produto.service.ProdutoServiceSpa;

import javax.persistence.EntityExistsException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ProdutoController.class)
public class ProdutoExceptionHandlerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ProdutoServiceSpa service;

    private ProdutoDto produtoDtoEnviado;
    private final String PRODUTO_API_URI = "/api/v1/produtos";

    @BeforeEach
    void setUp() {
        produtoDtoEnviado = ProdutoDtoBuilder.builder().build().buildProdutoDto();
    }

    @Test
    @DisplayName("Deve tentar criar produto com id existente e retornar 400")
    void criarProdutoCnpjExistenteTest() throws Exception {

        // given | cenário
        String json = new ObjectMapper().writeValueAsString(produtoDtoEnviado);

        // when | execução
        when(service.criar(produtoDtoEnviado)).thenThrow(new EntityExistsException(""));
        MockHttpServletRequestBuilder requestCriada = MockMvcRequestBuilders
                .post(PRODUTO_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        ResultActions perform = mockMvc.perform(requestCriada);

        // then | verificação
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("timestamp").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("errors").exists());
    }

    @Test
    @DisplayName("Deve tentar criar produto com plano vazio e retornar 400")
    void criarProdutoArgumentoInvalidoTest() throws Exception {

        // given | cenário
        produtoDtoEnviado.setPlano("");
        String json = new ObjectMapper().writeValueAsString(produtoDtoEnviado);

        // when | execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PRODUTO_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        ResultActions perform = mockMvc.perform(request);

        // then | verificação
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("timestamp").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("errors").exists());

    }

    @Test
    @DisplayName("Deve tentar criar produto com json desformatado e retornar 400")
    void criarProdutoJsonDesformatadoTest() throws Exception {

        // given | cenário
        String jsonDesformatado =
                "{" +
                        "\"cnpj\" \"38.067.491/0001-60\"," +
                        "\"nome\" \"Bruno e Oliver Contábil ME\"" +
                        "}";

        // when | execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PRODUTO_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonDesformatado);
        ResultActions perform = mockMvc.perform(request);

        // then | verificação
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("timestamp").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("errors").exists());
    }

}
