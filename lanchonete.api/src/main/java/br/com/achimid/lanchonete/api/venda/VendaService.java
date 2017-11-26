package br.com.achimid.lanchonete.api.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaItemRepository vendaItemRepository;

    @Autowired
    private VendaRepository vendaRepository;

    public List<Venda> findAll(){
        return (List<Venda>) vendaRepository.findAll();
    }

    public Venda findOne(Long id){
        return vendaRepository.findOne(id);
    }

    @Transactional
    public Venda
    checkouVenda(List<VendaItem> listaItens){
        Venda venda = new Venda();
        venda.setDataVenda(GregorianCalendar.getInstance().getTime());
        venda.setListaItens(listaItens);
        venda.getListaItens().forEach(item -> item.setVenda(venda));

        this.calculaValorFinal(venda);

        return vendaRepository.save(venda);
    }

    private void calculaValorFinal(Venda venda){
        if(venda == null) throw new IllegalArgumentException("A venda não pode ser nulla.");

        BigDecimal valorFinal = BigDecimal.ZERO;
        for(VendaItem item : venda.getListaItens()){
            valorFinal = valorFinal.add(item.getValor().multiply(item.getQtde()));
        }
        venda.setValorFinal(valorFinal);
    }


}
