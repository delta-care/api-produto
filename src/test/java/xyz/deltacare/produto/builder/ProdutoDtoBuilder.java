package xyz.deltacare.produto.builder;

import lombok.Builder;
import xyz.deltacare.produto.dto.ProdutoDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public class ProdutoDtoBuilder {

    private final String id;

    @Builder.Default
    private final String plano = "38067491000160";

    @Builder.Default
    private final String subplano = "Bruno e Oliver Contábil ME";

    @Builder.Default
    private final BigDecimal valor = new BigDecimal(120);

    @Builder.Default
    private final LocalDate dataInicioVigencia = LocalDate.now();

    private final LocalDate dataFimVigencia;

    public ProdutoDto buildProdutoDto() {

        return new ProdutoDto(
                id,
                plano,
                subplano,
                valor,
                dataInicioVigencia,
                dataFimVigencia
        );

    }
}
