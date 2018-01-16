package br.com.achimid.lanchonete.api.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController implements CategoriaControllerDoc {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> index(){
        return (List<Categoria>) categoriaService.findAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<Categoria> get(@PathVariable Long id){
        Categoria categoria = categoriaService.findOne(id);
        if(categoria == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public HttpEntity<Categoria> create(@RequestBody Categoria categoria){
        categoriaService.save(categoria);
        return ResponseEntity.ok(categoria);
    }

    @PutMapping
    public HttpEntity<?> update(@Valid @RequestBody Categoria categoria){
        return create(categoria);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        if(!categoriaService.exists(id))
            return ResponseEntity.notFound().build();
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
