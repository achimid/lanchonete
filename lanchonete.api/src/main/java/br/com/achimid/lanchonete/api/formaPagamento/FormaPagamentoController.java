package br.com.achimid.lanchonete.api.formaPagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/formaPagamento")
public class FormaPagamentoController implements FormaPagamentoControllerDoc{

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @GetMapping()
    public List<FormaPagamento> index(){
        return (List<FormaPagamento>) formaPagamentoService.findAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<FormaPagamento> get(@PathVariable Long id){
        FormaPagamento formaPagamento = formaPagamentoService.findOne(id);
        if(formaPagamento == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(formaPagamento);
    }

    @PostMapping
    public HttpEntity<FormaPagamento> create(@RequestBody FormaPagamento formaPagamento){
        formaPagamentoService.save(formaPagamento);
        return ResponseEntity.ok(formaPagamento);
    }

    @PutMapping
    public HttpEntity<?> update(@Valid @RequestBody FormaPagamento formaPagamento){
        return create(formaPagamento);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        if(!formaPagamentoService.exists(id))
            return ResponseEntity.notFound().build();
        formaPagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
