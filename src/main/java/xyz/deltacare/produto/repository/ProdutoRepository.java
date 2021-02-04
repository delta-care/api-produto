package xyz.deltacare.produto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.deltacare.produto.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
    @Query( value =
            " select distinct p " +
                    " from Produto p " +
                    " where 1=1 " +
                    "   and p.id like concat(:id,'%') " +
                    "   and p.plano like concat(:plano,'%')",
            countQuery =
                    " select count(p) " +
                            " from Produto p " +
                            " where 1=1 " +
                            "   and p.id like concat(:id,'%') " +
                            "   and p.plano like concat(:nome,'%')")
    Page<Produto> findAll(
            Pageable pageable,
            @Param("id") String id,
            @Param("nome") String nome);
}
