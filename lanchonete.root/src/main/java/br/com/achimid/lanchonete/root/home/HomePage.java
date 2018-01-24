package br.com.achimid.lanchonete.root.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Lourran on 15/07/2017.
 */
@Controller
public class HomePage {
    private static final String INDEX_PAGE = "pages/home/dashboard";
    private static final String VENDA_PAGE = "redirect:/venda";

    @GetMapping("/home")
    public ModelAndView index(){
        return new ModelAndView(VENDA_PAGE);
        //return new ModelAndView(INDEX_PAGE);
    }

}
