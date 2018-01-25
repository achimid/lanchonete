package br.com.achimid.lanchonete.root.controller.venda;

import br.com.achimid.lanchonete.root.base.BaseController;
import br.com.achimid.lanchonete.root.dto.CategoriaDTO;
import br.com.achimid.lanchonete.root.dto.ProdutoDTO;
import br.com.achimid.lanchonete.root.dto.VendaDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/venda")
public class VendaController extends BaseController{

    private static final String URL_PATH = "/venda";
    private static final Class CLAZZ = VendaDTO.class;

    private static final String PASSO_PRODUTO = "pages/venda/passoProduto";
    private static final String PASSO_OP_PRODUTO = "pages/venda/passoOpcoesProduto";
    private static final String NEW = "pages/venda/new";
    private static final String REDIRECT = "redirect:/venda";

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView(NEW)
                .addObject("venda", new VendaDTO())
                .addObject("categorias");
    }

    @GetMapping("/passoProduto/{idCategoria}")
    public ModelAndView passoProduto( @PathVariable Long idCategoria){
        return new ModelAndView(PASSO_PRODUTO)
                .addObject("produtos", produtoByCategoria(idCategoria));
    }

    @GetMapping("/passoOpcoesProduto/{idProduto}")
    public ModelAndView passoOpcoesProduto( @PathVariable Long idProduto){
        return new ModelAndView(PASSO_OP_PRODUTO)
                .addObject("opcoes", Collections.EMPTY_LIST)
                .addObject("produto", super.findOne("/produto", ProdutoDTO.class, idProduto));
    }

    private List<ProdutoDTO> produtoByCategoria(Long idCategoria){
        return findAll("/produto?idCategoria=" + idCategoria);
    }


    @ModelAttribute("categorias")
    public List<CategoriaDTO> allCategorias(){
        return findAll("/categoria");
    }

}
