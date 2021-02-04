package xyz.deltacare.produto.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.deltacare.produto.dto.ProdutoDto;
import xyz.deltacare.produto.domain.Produto;

import org.springframework.data.domain.Pageable;
import xyz.deltacare.produto.mapper.ProdutoMapper;
import xyz.deltacare.produto.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoServiceSpa implements ProdutoService {

    private final ProdutoRepository repository;
    private static final ProdutoMapper mapper = ProdutoMapper.INSTANCE;

    @Override
    public ProdutoDto criar(ProdutoDto produtoDto) {
        return salvar(produtoDto);
    }

    @Override
    //@Cacheable(value="produto", key="#root.args")
    public List<ProdutoDto> pesquisar(Pageable pageable, String id, String nome) {
        return repository.findAll(pageable, id, nome)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    //@CacheEvict(value="produto", allEntries = true)
    public ProdutoDto atualizar(ProdutoDto produtoDto) {
        return salvar(produtoDto);
    }

    @Transactional
    protected ProdutoDto salvar(ProdutoDto produtoDto) {
        Produto produto = mapper.toModel(produtoDto);
        Produto produtoSalvo = repository.save(produto);

        produto.setId(produtoSalvo.getId());

        return mapper.toDto(produto);
    }

}
