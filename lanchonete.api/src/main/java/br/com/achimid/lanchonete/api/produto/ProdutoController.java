package br.com.achimid.lanchonete.api.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produto")
public class ProdutoController implements ProdutoControllerDoc {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping()
    public List<Produto> index() {
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<Produto> get(@PathVariable Long id) {
        Produto produto = produtoService.findOne(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public HttpEntity<Produto> create(@RequestBody Produto produto) {
        produtoService.save(produto);
        return ResponseEntity.ok(produto);
    }

    @PutMapping
    public HttpEntity<?> update(@Valid @RequestBody Produto produto) {
        return create(produto);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        Produto produto = produtoService.findOne(id);
        if(produto == null)
            return ResponseEntity.notFound().build();
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
