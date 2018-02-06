package br.com.achimid.lanchonete.root.controller.venda;

import br.com.achimid.lanchonete.root.base.BaseController;
import br.com.achimid.lanchonete.root.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


import java.util.Collections;
import java.util.List;

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
    public HttpEntity<VendaDTO> checkoutVenda(@RequestBody VendaMesaDTO vendaMesa){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<VendaDTO> response = null;
        try {
            if(vendaMesa.getMesa() == null || vendaMesa.getMesa().getIdMesa() == null){
                response = restTemplate.postForEntity(URL_API + "/venda", vendaMesa.getVenda(), VendaDTO.class);
            }else{
                response = restTemplate.postForEntity(URL_API + "/venda/checkoutVendaMesa", vendaMesa, VendaDTO.class);
            }
            return ResponseEntity.ok(response.getBody());
        }catch (HttpClientErrorException e){
            VendaDTO venda = response.getBody();
            super.preparaErro(e, venda);
            return new ResponseEntity<VendaDTO>(venda, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
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
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<VendaDTO> response = null;
        VendaDTO venda = new VendaDTO();
        venda.setListaItens(itens);
        try {
            response = restTemplate.postForEntity(URL_API + "/venda/consolidaVenda", venda, VendaDTO.class);
            venda = response.getBody();
        }catch (HttpClientErrorException e){
            venda = response.getBody();
            super.preparaErro(e, venda);
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }

        for(VendaItemDTO vi : itens)
            vi.setProduto((ProdutoDTO) super.findOne("/produto", ProdutoDTO.class, vi.getProduto().getIdProduto()));
        return new ModelAndView(PASSO_CONFIRMAR)
                .addObject("itens", venda.getListaItens())
                .addObject("venda", venda);
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

    @ModelAttribute("formasPagamento")
    public List<CategoriaDTO> allFormasPagamento(){ return findAll("/formaPagamento"); }

}
