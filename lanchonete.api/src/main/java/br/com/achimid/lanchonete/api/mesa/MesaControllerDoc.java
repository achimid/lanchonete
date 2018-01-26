package br.com.achimid.lanchonete.api.mesa;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface MesaControllerDoc {

    @ApiOperation("Lista todos as mesas")
    public List<Mesa> index();

    @ApiOperation(value = "Retorna uma mesa com o id informado")
    public HttpEntity<Mesa> get(@PathVariable Long id);

    @ApiOperation(value = "Efetuado o cadastro de uma mesa")
    public HttpEntity<Mesa> create(@RequestBody Mesa mesa);

    @ApiOperation(value = "Atualiza as informações da mesa")
    public HttpEntity<?> update(@Valid @RequestBody Mesa mesa);

    @ApiOperation(value = "Remove uma mesa existente atravez do id informado")
    public HttpEntity<?> delete(@PathVariable Long id);

}
