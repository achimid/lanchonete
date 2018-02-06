package br.com.achimid.lanchonete.api.compra.venda;

import br.com.achimid.lanchonete.api.categoria.Categoria;
import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItem;
import br.com.achimid.lanchonete.api.compra.vendaMesa.VendaMesa;
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
    public HttpEntity<Venda> checkouVenda(@RequestBody Venda venda){
        validarVenda(venda);
        venda = vendaService.checkouVenda(venda, null);
        return ResponseEntity.ok(venda);
    }

    @PostMapping("/checkoutVendaMesa")
    public HttpEntity<Venda> checkouVenda(@RequestBody VendaMesa vendaMesa){
        validarVendaMesa(vendaMesa);
        Venda venda = vendaService.checkouVenda(vendaMesa.getVenda(), vendaMesa.getMesa());
        return ResponseEntity.ok(venda);
    }

    @PostMapping("/consolidaVenda")
    public HttpEntity<Venda> consolidaVenda(@RequestBody Venda venda){
        if(venda == null)
            throw new IllegalArgumentException("Venda não pode ser null");

        if(venda.getListaItens() == null || venda.getListaItens().isEmpty())
            throw new IllegalArgumentException("Lista de itens inválidos");

        vendaService.consolidaProdutosItens(venda);
        vendaService.calculaValorFinal(venda);
        return ResponseEntity.ok(venda);
    }

    private void validarVendaMesa(VendaMesa vendaMesa){
        if(vendaMesa == null)
            throw new IllegalArgumentException("VendaMesa não pode ser null");
        if(vendaMesa.getVenda() == null)
            throw new IllegalArgumentException("Venda não pode ser null");
        if(vendaMesa.getMesa() == null || vendaMesa.getMesa().getIdMesa() == null )
            throw new IllegalArgumentException("Mesa ou idMesa não pode ser null");
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
