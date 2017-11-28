package br.com.achimid.lanchonete.api.formaPagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> findAll(){
        return (List<FormaPagamento>) formaPagamentoRepository.findAll();
    }

    public FormaPagamento findOne(Long id) {
        return formaPagamentoRepository.findOne(id);
    }

    public void save(FormaPagamento formaPagamento) {
        formaPagamentoRepository.save(formaPagamento);
    }

    public void delete(Long id) {
        if (formaPagamentoRepository.exists(id)) {
            formaPagamentoRepository.delete(id);
        } else {
            throw new RuntimeException("Forma de pagamento não encontrada");
        }
    }

    public Boolean exists(Long id){
        return formaPagamentoRepository.exists(id);
    }
}
