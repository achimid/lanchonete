package br.com.achimid.lanchonete.api;

import br.com.achimid.lanchonete.api.base.TestBase;
import br.com.achimid.lanchonete.api.categoria.Categoria;
import br.com.achimid.lanchonete.api.categoria.CategoriaRepository;
import br.com.achimid.lanchonete.api.compra.vendaPagamento.VendaPagamento;
import br.com.achimid.lanchonete.api.formaPagamento.FormaPagamento;
import br.com.achimid.lanchonete.api.formaPagamento.FormaPagamentoRepository;
import br.com.achimid.lanchonete.api.mesa.Mesa;
import br.com.achimid.lanchonete.api.mesa.MesaRepository;
import br.com.achimid.lanchonete.api.produto.Produto;
import br.com.achimid.lanchonete.api.produto.ProdutoRepository;
import br.com.achimid.lanchonete.api.compra.venda.Venda;
import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItem;
import br.com.achimid.lanchonete.api.compra.venda.VendaService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VendaRestControllerTest extends TestBase {

    private List<Venda> produtos = new ArrayList<>();

    private final String END_POINT_VENDA = "/api/v1/venda";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private VendaService vendaService;

    private List<Venda> vendas = new ArrayList<>();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private List<VendaPagamento> getNewVendaPagamento(){
        List<VendaPagamento> vendaPagamentos = new ArrayList<>();

        VendaPagamento vendaPagamento = new VendaPagamento();
        vendaPagamento.setFormaPagamento(formaPagamentoRepository.save(new FormaPagamento("Dinheiro")));
        vendaPagamento.setValor(new BigDecimal(486.675));
        vendaPagamentos.add(vendaPagamento);

        vendaPagamento = new VendaPagamento();
        vendaPagamento.setFormaPagamento(formaPagamentoRepository.save(new FormaPagamento("Cartão")));
        vendaPagamento.setValor(new BigDecimal(486.675));
        vendaPagamentos.add(vendaPagamento);

        return vendaPagamentos;
    }

    private Produto getNewProduto(){
        Produto prod = new Produto();
        prod.setNome("Nome do produto");
        prod.setDescricao("Descrição do produto");
        prod.setValorVenda(new BigDecimal(18.50));
        prod.setValorCusto(new BigDecimal(15.50));

        Categoria cat = new Categoria();
        cat.setDescricao("Descrição da categoria");
        cat.setNome("Nome da Categoria");
        this.categoriaRepository.save(cat);

        prod.setCategoria(cat);

        return prod;
    }

    private VendaItem getNewVendaItem(){
        VendaItem item = new VendaItem();
        item.setQtde(new BigDecimal(5));
        item.setValor(new BigDecimal(64.89));

        Produto prod = produtoRepository.save(getNewProduto());
        item.setProduto(prod);

        return item;
    }

    private List<VendaItem> getNewListVendaItem(){
        List<VendaItem> itens = new ArrayList<>();
        itens.add(getNewVendaItem());
        itens.add(getNewVendaItem());
        itens.add(getNewVendaItem());

        return itens;
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Venda v = new Venda();
        v.setPagamentos(getNewVendaPagamento());
        v.setListaItens(getNewListVendaItem());

        this.vendas.add(this.vendaService.checkouVenda(v, null));

        Mesa mesa = new Mesa();
        mesa.setDescricao("Mesa 007-");
        mesaRepository.save(mesa);

        this.vendas.add(this.vendaService.checkouVenda(v, mesa));
    }

    @Test
    public void rest01VendaCheckout() throws Exception {
        Venda venda = new Venda();
        venda.setListaItens(getNewListVendaItem());
        venda.setPagamentos(getNewVendaPagamento());

        String jsonContent = json(venda);

        this.mockMvc.perform(post(END_POINT_VENDA)
                .contentType(contentType)
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void rest02GetVenda() throws Exception {
        mockMvc.perform(get(END_POINT_VENDA.concat("/").concat(vendas.get(0).getIdVenda().toString()))
                .contentType(contentType))
                .andExpect(status().isOk());
    }


}