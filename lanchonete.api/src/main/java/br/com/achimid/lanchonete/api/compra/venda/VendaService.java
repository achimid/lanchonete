package br.com.achimid.lanchonete.api.compra.venda;

import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItem;
import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItemRepository;
import br.com.achimid.lanchonete.api.compra.vendaPagamento.VendaPagamento;
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
    checkouVenda(List<VendaItem> listaItens, List<VendaPagamento> pagamentos){
        Venda venda = new Venda();
        venda.setDataVenda(GregorianCalendar.getInstance().getTime());
        venda.setListaItens(listaItens);
        venda.setPagamentos(pagamentos);

        venda.getListaItens().forEach(item -> item.setVenda(venda));
        venda.getPagamentos().forEach(pagamento -> pagamento.setVenda(venda));

        this.calculaValorFinal(venda);
        this.consolidaFormasPagamentos(venda);

        return vendaRepository.save(venda);
    }

    private void calculaValorFinal(Venda venda){
        if(venda == null) throw new IllegalArgumentException("A venda não pode ser nulla.");

        BigDecimal valorFinal = BigDecimal.ZERO;
        for(VendaItem item : venda.getListaItens()){
            valorFinal = valorFinal.add(item.getValor().multiply(item.getQtde()));
        }
        venda.setValorFinal(valorFinal.setScale(2, BigDecimal.ROUND_HALF_EVEN));
    }

    private void consolidaFormasPagamentos(Venda venda){
        if(venda == null) throw new IllegalArgumentException("A venda não pode ser nulla.");
        if(venda.getValorFinal() == null) throw new IllegalArgumentException("Valor final ainda não foi calculado.");

        BigDecimal somaPagamentos = BigDecimal.ZERO;
        for(VendaPagamento vendaPagamento : venda.getPagamentos()){
            somaPagamentos = somaPagamentos.add(vendaPagamento.getValor());
        }

        somaPagamentos = somaPagamentos.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        if(!somaPagamentos.equals(venda.getValorFinal()))
            throw new RuntimeException("A soma dos pagamentos difere do valor final do pedido");

    }


}
