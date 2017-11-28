package br.com.achimid.lanchonete.api.compra.venda;

import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItem;
import br.com.achimid.lanchonete.api.compra.vendaPagamento.VendaPagamento;
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
    public Venda getVenda(@PathVariable Long id){
        return vendaService.findOne(id);
    }

    @PostMapping
    public HttpEntity<Venda> checkouVenda(
            @RequestBody List<VendaItem> listaItens,
            @RequestBody List<VendaPagamento> pagamentos){
        if(listaItens == null || listaItens.isEmpty())
            throw new IllegalArgumentException("Lista de itens inválidos");

        if(pagamentos == null || pagamentos.isEmpty())
            throw new IllegalArgumentException("Lista de itens inválidos");

        Venda venda = vendaService.checkouVenda(listaItens, pagamentos);
        return ResponseEntity.ok(venda);
    }

}
