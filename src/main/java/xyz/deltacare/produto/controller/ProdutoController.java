package xyz.deltacare.produto.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xyz.deltacare.produto.dto.ProdutoDto;
import xyz.deltacare.produto.service.ProdutoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoController implements ProdutoControllerDocs {

    private final ProdutoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDto criar(@RequestBody @Valid ProdutoDto produtoDto) {
        return service.criar(produtoDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoDto> pesquisar(
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value="limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value="codigo", required = false, defaultValue = "") String id,
            @RequestParam(value="nome", required = false, defaultValue = "") String nome
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        return service.pesquisar(pageable, id, nome);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDto atualizar(@RequestBody @Valid ProdutoDto produtoDto) {
        return service.atualizar(produtoDto);
    }

}
