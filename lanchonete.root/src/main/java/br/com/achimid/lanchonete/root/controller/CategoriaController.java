package br.com.achimid.lanchonete.root.controller;

import br.com.achimid.lanchonete.root.base.BaseController;
import br.com.achimid.lanchonete.root.dto.CategoriaDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index(){
        return new ModelAndView(INDEX).addObject("categorias", super.findAll(URL_PATH));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        String msgErro = null;
        if(super.delete(URL_PATH, id, msgErro)){
            return new ModelAndView(REDIRECT)
                    .addObject("mensagem", getMessage("default.item.remove"));
        }else{
            return new ModelAndView(NEW.concat("/").concat(id.toString()))
                    .addObject("mensagem", getMessage("default.item.remove.error"));
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
    public ModelAndView save(CategoriaDTO categoria){
        if(super.save(URL_PATH, categoria)){
            return new ModelAndView(REDIRECT);
        }else{
            return get(categoria);
        }

    }


}
