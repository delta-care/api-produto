package xyz.deltacare.produto.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.deltacare.produto.dto.ProdutoDto;

import java.util.List;

@Api("Gestão de Produtos")
public interface ProdutoControllerDocs {

    @ApiOperation(value = "Criar produto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Produto criada com sucesso."),
            @ApiResponse(code = 400, message = "Mensagem de erro a ser definida.")
    })
    ProdutoDto criar(ProdutoDto produtoDto);

    @ApiOperation(value = "Pesquisar produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Empresa(s) encontrada(s) com sucesso.")
    })
    List<ProdutoDto> pesquisar(
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value="limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value="codigo", required = false, defaultValue = "") String id,
            @RequestParam(value="nome", required = false, defaultValue = "") String nome
    );

    @ApiOperation(value = "Atualizar produto por código")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Produto não encontrado."),
            @ApiResponse(code = 400, message = "Campo obrigatório não informado.")
    })
    ProdutoDto atualizar(ProdutoDto produtoDto);

}
