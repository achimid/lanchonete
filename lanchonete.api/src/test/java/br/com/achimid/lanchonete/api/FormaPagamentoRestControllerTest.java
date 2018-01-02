package br.com.achimid.lanchonete.api;

import br.com.achimid.lanchonete.api.base.TestBase;
import br.com.achimid.lanchonete.api.formaPagamento.FormaPagamento;
import br.com.achimid.lanchonete.api.formaPagamento.FormaPagamentoRepository;
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
public class FormaPagamentoRestControllerTest extends TestBase {

    private List<FormaPagamento> formaPagamentos = new ArrayList<>();

    private final String END_POINT_FORMA_PAGAMENTO = "/api/v1/formaPagamento";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    private FormaPagamento getNewFormaPagamento(){
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setNome("Nome forma pagamento");
        formaPagamento.setAtivo(Boolean.TRUE);

        return formaPagamento;
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        formaPagamentos.add(this.formaPagamentoRepository.save(getNewFormaPagamento()));
    }


    @Test
    public void rest01GetAllFormaPagamento() throws Exception {
        mockMvc.perform(post(END_POINT_FORMA_PAGAMENTO)
                .content(this.json(formaPagamentos.get(0)))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void rest02PostFormaPagamento() throws Exception {
        String contentJson = json(getNewFormaPagamento());

        this.mockMvc.perform(post(END_POINT_FORMA_PAGAMENTO)
                .contentType(contentType)
                .content(contentJson))
                .andExpect(status().isOk());
    }

    @Test
    public void rest03PutFormaPagamento() throws Exception {
        FormaPagamento formaPagamento = formaPagamentos.get(0);
        formaPagamento.setNome("Nome da forma de pagamento");

        String contentJson = json(formaPagamento);

        this.mockMvc.perform(put(END_POINT_FORMA_PAGAMENTO)
                .contentType(contentType)
                .content(contentJson))
                .andExpect(jsonPath("$.nome", is("Nome da forma de pagamento")))
                .andExpect(status().isOk());
    }

    @Test
    public void rest04GetFormaPagamento() throws Exception {
        mockMvc.perform(get(END_POINT_FORMA_PAGAMENTO.concat("/").concat(formaPagamentos.get(0).getIdFormaPagamento().toString()))
                .contentType(contentType))
                .andExpect(jsonPath("$.nome", is("Nome forma pagamento")))
                .andExpect(status().isOk());
    }

    @Test
    public void rest05DeleteFormaPagamento() throws Exception {
        this.mockMvc.perform(delete(END_POINT_FORMA_PAGAMENTO.concat("/").concat(formaPagamentos.get(0).getIdFormaPagamento().toString()))
                .contentType(contentType))
                .andExpect(status().isNoContent());
    }

    @Test
    public void rest06DeleteFormaPagamentoNotFound() throws Exception {
        this.mockMvc.perform(delete(END_POINT_FORMA_PAGAMENTO.concat("/0"))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

}