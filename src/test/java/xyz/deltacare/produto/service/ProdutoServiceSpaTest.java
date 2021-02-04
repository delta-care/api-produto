package xyz.deltacare.produto.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import xyz.deltacare.produto.builder.ProdutoDtoBuilder;
import xyz.deltacare.produto.domain.Produto;
import xyz.deltacare.produto.dto.ProdutoDto;
import xyz.deltacare.produto.mapper.ProdutoMapper;
import xyz.deltacare.produto.repository.ProdutoRepository;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ProdutoServiceSpaTest {

    @InjectMocks
    private ProdutoServiceSpa service;
    @Mock private ProdutoRepository repository;

    private Pageable pageable;
    private ProdutoDto produtoDtoEnviado;
    private ProdutoDto produtoDtoRetornado;
    private List<ProdutoDto> produtosDtoRetornados;
    private Produto produtoRetornado;
    private Page<Produto> produtosRetornadosPage;
    private final ProdutoMapper empresaMapper = ProdutoMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);

        produtoDtoEnviado = ProdutoDtoBuilder.builder().build().buildProdutoDto();
        produtoDtoRetornado = ProdutoDtoBuilder.builder().id("637832").build().buildProdutoDto();
        produtosDtoRetornados = Collections.singletonList(ProdutoDtoBuilder.builder().id("637832").build().buildProdutoDto());

        produtoRetornado = empresaMapper.toModel(produtoDtoRetornado);
        produtosRetornadosPage = new PageImpl<>(produtosDtoRetornados.stream().map(empresaMapper::toModel).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Deve tentar criar um produto e retornar dados iguais.")
    void criarEmpresaTest() {

        // given | cenário
        // setUp

        // when | execução
        when(repository.save(any(Produto.class))).thenReturn(produtoRetornado);
        ProdutoDto produtoDtoRetornado = service.criar(produtoDtoEnviado);

        // then | verificação
        assertThat(produtoDtoRetornado).isEqualTo(this.produtoDtoRetornado);

    }

    @Test
    @DisplayName("Deve tentar pesquisar produto por código e retornar dados iguais.")
    void pesquisarEmpresaPorCodigoTest() {

        // given | cenário
        String codigoEnviado = "637832";

        // when | execução
        when(repository.findAll(pageable, codigoEnviado, "")).thenReturn(produtosRetornadosPage);
        List<ProdutoDto> produtoDtoRetornados = service.pesquisar(pageable, codigoEnviado, "");

        // then | verificação
        assertThat(produtoDtoRetornados).isEqualTo(this.produtosDtoRetornados);

    }

    @Test
    @DisplayName("Deve tentar pesquisar produto por nome e retornar dados iguais.")
    void pesquisarEmpresaPorNomeTest() {

        // given | cenário
        String nomeEnviado = produtoDtoRetornado.getPlano().split(" ")[0];

        // when | execução
        when(repository.findAll(pageable, "", nomeEnviado)).thenReturn(produtosRetornadosPage);
        List<ProdutoDto> produtoDtoRetornados = service.pesquisar(pageable, "", nomeEnviado);

        // then | verificação
        assertThat(produtoDtoRetornados).isEqualTo(this.produtosDtoRetornados);

    }

    @Test
    @DisplayName("Deve tentar atualizar um produto e retornar dados iguais.")
    void atualizarEmpresaTest() {

        // given | cenário
        // setUp

        // when | execução
        when(repository.save(any(Produto.class))).thenReturn(this.produtoRetornado);
        ProdutoDto produtoDtoAtualizado = service.atualizar(this.produtoDtoRetornado);

        // then | verificação
        assertThat(produtoDtoAtualizado).isEqualTo(produtoDtoRetornado);

    }

}
