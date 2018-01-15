package br.com.achimid.lanchonete.api.categoria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends
        CrudRepository<Categoria, Long>, QueryByExampleExecutor<Categoria> {

}
