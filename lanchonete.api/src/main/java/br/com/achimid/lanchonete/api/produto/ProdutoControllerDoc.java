package br.com.achimid.lanchonete.api.produto;

import br.com.achimid.lanchonete.api.categoria.Categoria;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface ProdutoControllerDoc {

    @ApiOperation("Lista todos os produtos ou filtrar por categoria")
    public List<Produto> index(
            @RequestParam(value = "idCategoria", required = false) Long idCategoria);

    @ApiOperation(value = "Retorna um produto com o id informado")
    public HttpEntity<Produto> get(@PathVariable Long id);

    @ApiOperation(value = "Efetuado o cadastro de uma produto")
    public HttpEntity<Produto> create(@RequestBody Produto produto);

    @ApiOperation(value = "Atualiza as informações do produto")
    public HttpEntity<?> update(@Valid @RequestBody Produto produto);

    @ApiOperation(value = "Remove um produto existente atravez do id informado")
    public HttpEntity<?> delete(@PathVariable Long id);

}
