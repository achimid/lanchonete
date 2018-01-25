package br.com.achimid.lanchonete.api.produto;

import br.com.achimid.lanchonete.api.categoria.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

    List<Produto> findByCategoriaIdCategoria(Long idCategoria);

}
