package br.com.ecommerce.bff.adm.ecommerceadm.product;

import br.com.ecommerce.bff.adm.ecommerceadm.category.CategoryClient;
import br.com.ecommerce.bff.adm.ecommerceadm.category.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final String PRODUCT_INDEX = "pages/product/index";
    private static final String PRODUCT_NEW = "pages/product/new";
    private static final String PRODUCT_REDIRECT = "redirect:/products";

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CategoryClient categoryClient;


    private ModelAndView get(ProductDTO product){
        List<CategoryDTO> categories = categoryClient.findAll();
        return new ModelAndView(PRODUCT_NEW)
                .addObject("product", product)
                .addObject("categories", categories);
    }

    @GetMapping
    public ModelAndView index(){
        List<ProductDTO> products = productClient.findAll();
        return new ModelAndView(PRODUCT_INDEX).addObject("products", products);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        if(productClient.delete(id)){
            return new ModelAndView(PRODUCT_REDIRECT)
                    .addObject("mensage", "Excluido com sucesso.");
        }else{
            return new ModelAndView(PRODUCT_REDIRECT)
                    .addObject("mensage", "Erro ao excluir.");
        }

    }

    @GetMapping("/{id}")
    public ModelAndView get(@PathVariable Long id){
        return get(productClient.findOne(id));
    }

    @GetMapping("/new")
    public ModelAndView get(){
        return get(new ProductDTO());
    }

    @PostMapping("/new")
    public ModelAndView save(ProductDTO product){
        if(productClient.save(product)){
            return new ModelAndView(PRODUCT_REDIRECT);
        }else{
            return get(product);
        }

    }


}
