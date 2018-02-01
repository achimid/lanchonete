package br.com.achimid.lanchonete.api.compra.venda;

import br.com.achimid.lanchonete.api.categoria.Categoria;
import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItem;
import br.com.achimid.lanchonete.api.compra.vendaPagamento.VendaPagamento;
import br.com.achimid.lanchonete.api.mesa.Mesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venda")
public class VendaController implements VendaControllerDoc {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public List<Venda> index(){
        return vendaService.findAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<Venda> getVenda(@PathVariable Long id){
        Venda venda = vendaService.findOne(id);
        if(venda == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(venda);
    }

    @PostMapping
    public HttpEntity<Venda> checkouVenda(@RequestBody Venda venda, @RequestBody Mesa mesa){
        validarVenda(venda);
        venda = vendaService.checkouVenda(venda, mesa);
        return ResponseEntity.ok(venda);
    }

    private void validarVenda(Venda venda){
        if(venda == null)
            throw new IllegalArgumentException("Venda não pode ser null");

        if(venda.getListaItens() == null || venda.getListaItens().isEmpty())
            throw new IllegalArgumentException("Lista de itens inválidos");

        if(venda.getPagamentos() == null || venda.getPagamentos().isEmpty())
            throw new IllegalArgumentException("Lista de itens inválidos");
    }

}
