package br.com.achimid.lanchonete.api;

import br.com.achimid.lanchonete.api.base.TestBase;
import br.com.achimid.lanchonete.api.categoria.Categoria;
import br.com.achimid.lanchonete.api.categoria.CategoriaRepository;
import br.com.achimid.lanchonete.api.produto.Produto;
import br.com.achimid.lanchonete.api.produto.ProdutoRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class ProdutoRestControllerTest extends TestBase {

    private Produto produto;

    private List<Produto> produtos = new ArrayList<>();

    private final String END_POINT_PRODUTO = "/api/v1/produto";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

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

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        produtos.add(this.produtoRepository.save(getNewProduto()));
    }


    @Test
    public void rest01GetAllProdutos() throws Exception {
        mockMvc.perform(get(END_POINT_PRODUTO)
                .content(this.json(produtos.get(0)))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void rest02PostProduto() throws Exception {
        String produtoJson = json(getNewProduto());

        this.mockMvc.perform(post(END_POINT_PRODUTO)
                .contentType(contentType)
                .content(produtoJson))
                .andExpect(status().isOk());
    }

    @Test
    public void rest03PutProduto() throws Exception {
        Produto prod = produtos.get(0);
        prod.setNome("Nome 2 do produto");

        String produtoJson = json(prod);

        this.mockMvc.perform(put(END_POINT_PRODUTO)
                .contentType(contentType)
                .content(produtoJson))
                .andExpect(jsonPath("$.nome", is("Nome 2 do produto")))
                .andExpect(status().isOk());
    }

    @Test
    public void rest04GetProduto() throws Exception {
        mockMvc.perform(get(END_POINT_PRODUTO.concat("/").concat(produtos.get(0).getId().toString()))
                .contentType(contentType))
                .andExpect(jsonPath("$.nome", is("Nome do produto")))
                .andExpect(status().isOk());
    }

    @Test
    public void rest05DeleteProduto() throws Exception {
        this.mockMvc.perform(delete(END_POINT_PRODUTO.concat("/").concat(produtos.get(0).getId().toString()))
                .contentType(contentType))
                .andExpect(status().isNoContent());
    }

    @Test
    public void rest06DeleteProdutoNotFound() throws Exception {
        this.mockMvc.perform(delete(END_POINT_PRODUTO.concat("/0"))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

}