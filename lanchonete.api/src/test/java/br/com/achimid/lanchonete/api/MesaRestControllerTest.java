package br.com.achimid.lanchonete.api;

import br.com.achimid.lanchonete.api.base.TestBase;
import br.com.achimid.lanchonete.api.mesa.Mesa;
import br.com.achimid.lanchonete.api.mesa.MesaRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MesaRestControllerTest extends TestBase {

    private List<Mesa> mesas = new ArrayList<>();

    private final String END_POINT_MESA = "/api/v1/mesa";

    @Autowired
    private MesaRepository mesaRepository;

    private Mesa getNewMesa(){
        Mesa mesa = new Mesa();
        mesa.setDescricao("Mesa 007");
        mesa.setStatus("Não salvar");

        return mesa;
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        mesas.add(this.mesaRepository.save(getNewMesa()));
    }


    @Test
    public void rest01GetAllMesa() throws Exception {
        mockMvc.perform(post(END_POINT_MESA)
                .content(this.json(mesas.get(0)))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void rest02PostMesa() throws Exception {
        String contentJson = json(getNewMesa());

        this.mockMvc.perform(post(END_POINT_MESA)
                .contentType(contentType)
                .content(contentJson))
                .andExpect(status().isOk());
    }

    @Test
    public void rest03PutMesa() throws Exception {
        Mesa mesa = mesas.get(0);
        mesa.setDescricao("Mesa 0096");

        String contentJson = json(mesa);

        this.mockMvc.perform(put(END_POINT_MESA)
                .contentType(contentType)
                .content(contentJson))
                .andExpect(jsonPath("$.descricao", is("Mesa 0096")))
                .andExpect(status().isOk());
    }

    @Test
    public void rest04GetMesa() throws Exception {
        mockMvc.perform(get(END_POINT_MESA.concat("/").concat(mesas.get(0).getIdMesa().toString()))
                .contentType(contentType))
                .andExpect(jsonPath("$.descricao", is("Mesa 007")))
                .andExpect(status().isOk());
    }

    @Test
    public void rest05DeleteMesa() throws Exception {
        this.mockMvc.perform(delete(END_POINT_MESA.concat("/").concat(mesas.get(0).getIdMesa().toString()))
                .contentType(contentType))
                .andExpect(status().isNoContent());
    }

    @Test
    public void rest06DeleteMesaNotFound() throws Exception {
        this.mockMvc.perform(delete(END_POINT_MESA.concat("/0"))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

}