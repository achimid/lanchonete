package br.com.ecommerce.bff.adm.ecommerceadm.category;

import br.com.ecommerce.bff.adm.ecommerceadm.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController{

    private static final String INDEX = "pages/category/index";
    private static final String NEW = "pages/category/new";
    private static final String REDIRECT = "redirect:/categories";

    @Autowired
    private CategoryClient categoryClient;

    private ModelAndView get(CategoryDTO category){
        return new ModelAndView(NEW)
                .addObject("category",category);
    }

    @GetMapping
    public ModelAndView index(){
        List<CategoryDTO> categories = categoryClient.findAll();
        return new ModelAndView(INDEX).addObject("categories", categories);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        if(categoryClient.delete(id)){
            return new ModelAndView(REDIRECT)
                    .addObject("mensage", getMessage("default.item.remove"));
        }else{
            return new ModelAndView(REDIRECT)
                    .addObject("mensage", getMessage("default.item.remove.error"));
        }

    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable Long id){
        return get(categoryClient.findOne(id));
    }

    @GetMapping("/new")
    public ModelAndView get(){
        return get(new CategoryDTO());
    }

    @PostMapping("/new")
    public ModelAndView save(CategoryDTO category){
        if(categoryClient.save(category)){
            return new ModelAndView(REDIRECT);
        }else{
            return get(category);
        }

    }


}
