package br.com.achimid.lanchonete.root.controller;

import br.com.achimid.lanchonete.root.base.BaseController;
import br.com.achimid.lanchonete.root.dto.CategoriaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController extends BaseController{

    private static final String URL_PATH = "/categoria";
    private static final Class CATEGORIA_CLAZZ = CategoriaDTO.class;

    private static final String INDEX = "pages/categoria/index";
    private static final String NEW = "pages/categoria/new";
    private static final String REDIRECT = "redirect:/categoria";

    private ModelAndView get(CategoriaDTO categoria){
        return new ModelAndView(NEW)
                .addObject("categoria", categoria);
    }

    @GetMapping
    public ModelAndView index(
            @RequestParam(required = false) String filtro){
        return new ModelAndView(INDEX)
                .addObject("categorias", search(filtro));
    }

    @DeleteMapping("{id}")
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if(super.delete(URL_PATH, id)){
            redirectAttributes.addFlashAttribute("msgSuccess", getMessage("default.item.remove"));
            return new ModelAndView(REDIRECT);
        }else{
            return get(id)
                    .addObject("msgErros", getMessage("default.item.remove.error"));
        }
    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable Long id){
        return get((CategoriaDTO) super.findOne(URL_PATH, CATEGORIA_CLAZZ, id));
    }

    @GetMapping("/new")
    public ModelAndView get(){
        return get(new CategoriaDTO());
    }

    @PostMapping("/new")
    public ModelAndView save(CategoriaDTO categoria, RedirectAttributes redirectAttributes){
        if(super.save(URL_PATH, categoria)){
            redirectAttributes.addFlashAttribute("msgSuccess", getMessage("deafult.item.created"));
            return new ModelAndView(REDIRECT);
        }else{
            return get(categoria);
        }

    }

    private List<CategoriaDTO> search(String filtro){
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.
                fromHttpUrl(super.URL_API.concat(URL_PATH))
                .queryParam("nome", filtro);

        String s = builder.build().encode().toString();

        ResponseEntity<List> response = restTemplate
                .getForEntity(builder.build().encode().toString(), List.class);

        return response.getBody();
    }


}
