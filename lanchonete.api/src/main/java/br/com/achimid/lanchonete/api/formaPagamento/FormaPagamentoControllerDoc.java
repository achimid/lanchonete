package br.com.achimid.lanchonete.api.formaPagamento;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface FormaPagamentoControllerDoc {

    @ApiOperation(value = "Retorna todos as formas de pagamento")
    public List<FormaPagamento> index();

    @ApiOperation(value = "Retorna uma forma de pagamento especifica informando o id da forma de pagamento")
    public HttpEntity<FormaPagamento> get(@PathVariable Long id);

    @ApiOperation(value = "Efetuado o cadastro de uma forma de pagamento")
    public HttpEntity<FormaPagamento> create(@RequestBody FormaPagamento formaPagamento);

    @ApiOperation(value = "Atualiza as informações da forma de pagamento")
    public HttpEntity<?> update(@Valid @RequestBody FormaPagamento formaPagamento);

    @ApiOperation(value = "Remove uma forma de pagamento existente")
    public HttpEntity<?> delete(@PathVariable Long id);

}
