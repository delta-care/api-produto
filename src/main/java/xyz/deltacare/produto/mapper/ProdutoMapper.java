package xyz.deltacare.produto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.deltacare.produto.domain.Produto;
import xyz.deltacare.produto.dto.ProdutoDto;

@Mapper
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);
    Produto toModel(ProdutoDto produtoDto);
    ProdutoDto toDto(Produto produto);
}

