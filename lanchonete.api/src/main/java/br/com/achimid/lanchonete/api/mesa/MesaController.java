package br.com.achimid.lanchonete.api.mesa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mesa")
public class MesaController implements MesaControllerDoc {

    @Autowired
    private MesaService mesaService;

    @GetMapping()
    public List<Mesa> index() {
        return mesaService.findAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<Mesa> get(@PathVariable Long id) {
        Mesa mesa = mesaService.findOne(id);
        return ResponseEntity.ok(mesa);
    }

    @PostMapping
    public HttpEntity<Mesa> create(@RequestBody Mesa mesa) {
        mesaService.save(mesa);
        return ResponseEntity.ok(mesa);
    }

    @PutMapping
    public HttpEntity<?> update(@Valid @RequestBody Mesa mesa) {
        return create(mesa);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        Mesa mesa = mesaService.findOne(id);
        if(mesa == null)
            return ResponseEntity.notFound().build();
        mesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
