package br.com.achimid.lanchonete.api.compra.venda;

import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItem;
import br.com.achimid.lanchonete.api.compra.vendaPagamento.VendaPagamento;
import br.com.achimid.lanchonete.api.mesa.Mesa;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface VendaControllerDoc {

    @ApiOperation("Lista todas as vendas")
    public List<Venda> index();

    @ApiOperation("Busca venda por id")
    public HttpEntity<Venda> getVenda(@PathVariable Long id);

    @ApiOperation("Metodo para finalizar venda. Informando a Venda e ou a Mesa")
    public HttpEntity<Venda> checkouVenda(@RequestBody Venda venda, @RequestBody Mesa mesa);

}
