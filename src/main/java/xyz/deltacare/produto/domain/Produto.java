package xyz.deltacare.produto.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String plano;
    private String subplano;
    private BigDecimal valor;

    @Column(name = "data_inicio_vigencia")
    private LocalDate dataInicioVigencia;

    @Column(name = "data_fim_vigencia")
    private LocalDate dataFimVigencia;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Produto))
            return false;

        Produto other = (Produto) o;

        return id != null &&
                id.equals(other.getId());
    }
}
