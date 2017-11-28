package br.com.achimid.lanchonete.api;

import br.com.achimid.lanchonete.api.base.TestBase;
import br.com.achimid.lanchonete.api.categoria.Categoria;
import br.com.achimid.lanchonete.api.categoria.CategoriaRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoriaRestControllerTest extends TestBase {

    private Categoria categoria;

    private List<Categoria> categorias = new ArrayList<>();

    private final String END_POINT_CATEGORIA = "/api/v1/categoria";

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Categoria getNewCategoria(){
        Categoria cat = new Categoria();
        cat.setDescricao("Descrição da categoria");
        cat.setNome("Nome da Categoria");

        return cat;
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        categorias.add(this.categoriaRepository.save(getNewCategoria()));
    }


    @Test
    public void rest01GetAllCategoria() throws Exception {
        mockMvc.perform(post(END_POINT_CATEGORIA)
                .content(this.json(categorias.get(0)))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void rest02PostCategoria() throws Exception {
        String categoriaJson = json(getNewCategoria());

        this.mockMvc.perform(post(END_POINT_CATEGORIA)
                .contentType(contentType)
                .content(categoriaJson))
                .andExpect(status().isOk());
    }

    @Test
    public void rest03PutCategoria() throws Exception {
        Categoria cat = categorias.get(0);
        cat.setNome("Nome 2 da Categoria");

        String categoriaJson = json(cat);

        this.mockMvc.perform(put(END_POINT_CATEGORIA)
                .contentType(contentType)
                .content(categoriaJson))
                .andExpect(jsonPath("$.nome", is("Nome 2 da Categoria")))
                .andExpect(status().isOk());
    }

    @Test
    public void rest04GetCategoria() throws Exception {
        mockMvc.perform(get(END_POINT_CATEGORIA.concat("/").concat(categorias.get(0).getIdCategoria().toString()))
                .contentType(contentType))
                .andExpect(jsonPath("$.nome", is("Nome da Categoria")))
                .andExpect(status().isOk());
    }

    @Test
    public void rest05DeleteCategoria() throws Exception {
        this.mockMvc.perform(delete(END_POINT_CATEGORIA.concat("/").concat(categorias.get(0).getIdCategoria().toString()))
                .contentType(contentType))
                .andExpect(status().isNoContent());
    }

    @Test
    public void rest06DeleteCategoriaNotFound() throws Exception {
        this.mockMvc.perform(delete(END_POINT_CATEGORIA.concat("/0"))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }


    //@Test
    //public void categoriaNotFound() throws Exception {
            //    mockMvc.perform(post(END_POINT_CATEGORIA)
            //            .content(this.json(new Categoria()))
            //            .contentType(contentType))
    //            .andExpect(status().isNotFound());
    //}

    //@Test
    //public void readSingleCategoria() throws Exception {
    //    mockMvc.perform(get(END_POINT_CATEGORIA.concat(this.categorias.get(0).getId().toString())))
    //            .andExpect(status().isOk())
    //            .andExpect(content().contentType(contentType))
    //            .andExpect(jsonPath("$.id", is(this.categorias.get(0).getId().intValue())))
    //            .andExpect(jsonPath("$.descricao", is("Descrição da categoria")));
    //}

    //@Test
    //public void createBookmark() throws Exception {
        //String bookmarkJson = json(new Bookmark(
        //        this.account, "http://spring.io", "a bookmark to the best resource for Spring news and information"));

        //this.mockMvc.perform(post("/" + userName + "/bookmarks")
        //              .contentType(contentType)
        //              .content(bookmarkJson))
        //        .andExpect(status().isCreated());
    //}




}