package br.com.achimid.lanchonete.root.controller;

import br.com.achimid.lanchonete.root.base.BaseController;
import br.com.achimid.lanchonete.root.dto.CategoriaDTO;
import br.com.achimid.lanchonete.root.dto.ProdutoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController extends BaseController{

    private static final String URL_PATH = "/produto";
    private static final Class CLAZZ = ProdutoDTO.class;

    private static final String INDEX = "pages/produto/index";
    private static final String NEW = "pages/produto/new";
    private static final String REDIRECT = "redirect:/produto";

    private ModelAndView get(ProdutoDTO produto){
        return new ModelAndView(NEW)
                .addObject("produto", produto);
    }

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView(INDEX).addObject("produtos", super.findAll(URL_PATH));
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
        return get((ProdutoDTO) super.findOne(URL_PATH, CLAZZ, id));
    }

    @GetMapping("/new")
    public ModelAndView get(){
        return get(new ProdutoDTO());
    }

    @PostMapping("/new")
    public ModelAndView save(ProdutoDTO produto, BindingResult result, RedirectAttributes redirectAttributes){
        if(super.save(URL_PATH, produto)){
            redirectAttributes.addFlashAttribute("msgSuccess", getMessage("deafult.item.created"));
            return new ModelAndView(REDIRECT);
        }else{
            return get(produto);
        }

    }

    @ModelAttribute("allCategorias")
    public List<CategoriaDTO> allCategorias(){
        return findAll("/categoria");
    }
}
