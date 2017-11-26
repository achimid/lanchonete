package br.com.achimid.lanchonete.api.categoria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface CategoriaControllerDoc {

    @ApiOperation(value = "Retorna todos as categorias")
    public List<Categoria> index();

    @ApiOperation(value = "Retorna uma categoria especifica informando o id da categoria")
    public HttpEntity<Categoria> get(@PathVariable Long id);

    @ApiOperation(value = "Efetuado o cadastro de uma categoria")
    public HttpEntity<Categoria> create(@RequestBody Categoria categoria);

    @ApiOperation(value = "Atualiza as informações da categoria")
    public HttpEntity<?> update(@Valid @RequestBody Categoria categoria);

    @ApiOperation(value = "Remove uma categoria existente")
    public HttpEntity<?> delete(@PathVariable Long id);

}
