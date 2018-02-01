package br.com.achimid.lanchonete.root.controller.venda;

import br.com.achimid.lanchonete.root.base.BaseController;
import br.com.achimid.lanchonete.root.dto.*;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/venda")
public class VendaController extends BaseController{

    private static final String URL_PATH = "/venda";
    private static final Class CLAZZ = VendaDTO.class;

    private static final String PASSO_PRODUTO = "pages/venda/passoProduto";
    private static final String PASSO_OP_PRODUTO = "pages/venda/passoOpcoesProduto";
    private static final String PASSO_CONFIRMAR = "pages/venda/passoConfirmar";
    private static final String NEW = "pages/venda/new";
    private static final String REDIRECT = "redirect:/venda";

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView(NEW)
                .addObject("venda", new VendaDTO())
                .addObject("categorias");
    }

    @PostMapping
    public HttpEntity<VendaDTO> checkoutVenda(
            @RequestBody(required = false) VendaDTO venda, @RequestBody(required = false) MesaDTO mesa){

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("venda", venda);
        map.add("mesa", mesa);

        ResponseEntity<VendaDTO> response = null;
        try {
            response = restTemplate.postForEntity(URL_API, map, VendaDTO.class);
            return ResponseEntity.ok(response.getBody());
        }catch (HttpClientErrorException e){
            super.preparaErro(e, venda);
            return new ResponseEntity<>(venda, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @PostMapping("/passoConfirmar")
    public ModelAndView passoConfirmar( @RequestBody List<VendaItemDTO> itens){
        for(VendaItemDTO vi : itens)
            vi.setProduto((ProdutoDTO) super.findOne("/produto", ProdutoDTO.class, vi.getProduto().getIdProduto()));
        return new ModelAndView(PASSO_CONFIRMAR)
                .addObject("itens", itens);
    }

    private List<ProdutoDTO> produtoByCategoria(Long idCategoria){
        return findAll("/produto?idCategoria=" + idCategoria);
    }


    @ModelAttribute("categorias")
    public List<CategoriaDTO> allCategorias(){
        return findAll("/categoria");
    }

    @ModelAttribute("mesas")
    public List<CategoriaDTO> allMesas(){
        return findAll("/mesa");
    }

}
