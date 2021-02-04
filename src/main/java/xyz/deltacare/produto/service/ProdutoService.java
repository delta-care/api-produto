package xyz.deltacare.produto.service;

import org.springframework.data.domain.Pageable;
import xyz.deltacare.produto.dto.ProdutoDto;

import java.util.List;

public interface ProdutoService {
    ProdutoDto criar(ProdutoDto produtoDto);
    List<ProdutoDto> pesquisar(Pageable pageable, String id, String nome);
    ProdutoDto atualizar(ProdutoDto produtoDto);
}
