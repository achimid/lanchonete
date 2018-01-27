package br.com.achimid.lanchonete.root.controller;

import br.com.achimid.lanchonete.root.base.BaseController;
import br.com.achimid.lanchonete.root.dto.MesaDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/mesa")
public class MesaController extends BaseController{

    private static final String URL_PATH = "/mesa";
    private static final Class CLAZZ = MesaDTO.class;

    private static final String INDEX = "pages/mesa/index";
    private static final String NEW = "pages/mesa/new";
    private static final String REDIRECT = "redirect:/mesa";

    private ModelAndView get(MesaDTO mesa){
        return new ModelAndView(NEW)
                .addObject("mesa", mesa);
    }

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView(INDEX).addObject("mesas", super.findAll(URL_PATH));
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
        return get((MesaDTO) super.findOne(URL_PATH, CLAZZ, id));
    }

    @GetMapping("/new")
    public ModelAndView get(){
        return get(new MesaDTO());
    }

    @PostMapping("/new")
    public ModelAndView save(MesaDTO mesa, BindingResult result, RedirectAttributes redirectAttributes){
        if(super.save(URL_PATH, mesa)){
            redirectAttributes.addFlashAttribute("msgSuccess", getMessage("deafult.item.created"));
            return new ModelAndView(REDIRECT);
        }else{
            return get(mesa);
        }

    }

}
