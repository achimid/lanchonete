package br.com.achimid.lanchonete.api.venda;

import br.com.achimid.lanchonete.api.produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public List<Venda> index(){
        return vendaService.findAll();
    }

    @PostMapping
    public HttpEntity<Venda> checkouVenda(@RequestBody List<VendaItem> listaItens){
        if(listaItens == null || listaItens.isEmpty())
            throw new IllegalArgumentException("Lista de itens inválidos");

        Venda venda = vendaService.checkouVenda(listaItens);
        return ResponseEntity.ok(venda);
    }

}
