package br.com.ecommerce.bff.adm.ecommerceadm.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Lourran on 15/07/2017.
 */
@Controller
public class HomePage {
    private static final String INDEX_PAGE = "pages/home/dashboard";

    @GetMapping("/home")
    public ModelAndView index(){
        return new ModelAndView(INDEX_PAGE);
    }

}
