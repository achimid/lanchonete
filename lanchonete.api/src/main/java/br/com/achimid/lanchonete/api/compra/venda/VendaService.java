package br.com.achimid.lanchonete.api.compra.venda;

import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItem;
import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItemRepository;
import br.com.achimid.lanchonete.api.compra.vendaMesa.VendaMesa;
import br.com.achimid.lanchonete.api.compra.vendaMesa.VendaMesaRepository;
import br.com.achimid.lanchonete.api.compra.vendaPagamento.VendaPagamento;
import br.com.achimid.lanchonete.api.compra.vendaPagamento.VendaPagamentoRepository;
import br.com.achimid.lanchonete.api.mesa.Mesa;
import br.com.achimid.lanchonete.api.mesa.MesaStatus;
import br.com.achimid.lanchonete.api.produto.ProdutoRepository;
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

    @Autowired
    private VendaPagamentoRepository vendaPagamentoRepository;

    @Autowired
    private VendaMesaRepository vendaMesaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Venda> findAll(){
        return (List<Venda>) vendaRepository.findAll();
    }

    public Venda findOne(Long id){
        return vendaRepository.findOne(id);
    }

    @Transactional
    public Venda checkouVenda(Venda venda, Mesa mesa){
        if(venda == null){
            throw new IllegalArgumentException("Venda não pode ser nulla");
        }if(venda.getListaItens() == null || venda.getListaItens().isEmpty()){
            throw new IllegalArgumentException("A venda deve ter ao menu um item");
        }

        venda.setDataVenda(GregorianCalendar.getInstance().getTime());
        this.consolidaProdutosItens(venda);
        this.calculaValorFinal(venda);

        venda.getListaItens().forEach(item -> item.setVenda(venda));

        if(mesa == null || mesa.getIdMesa() == null){
            //Venda sem Mesa

            if(venda.getPagamentos() == null || venda.getPagamentos().isEmpty()){
                throw new IllegalArgumentException("O pagamento não pode ser null");
            }

            venda.getPagamentos().forEach(pagamento -> pagamento.setVenda(venda));
            this.consolidaFormasPagamentos(venda);

            vendaRepository.save(venda);
            vendaPagamentoRepository.save(venda.getPagamentos());
            vendaItemRepository.save(venda.getListaItens());


        }else{
            // Venda de Mesa
            VendaMesa vm = new VendaMesa();
            vm.setMesa(mesa);
            vm.setVenda(venda);
            vm.setStatus(MesaStatus.OCUPADO);

            vendaRepository.save(venda);
            vendaItemRepository.save(venda.getListaItens());
            vendaMesaRepository.save(vm);
        }

        return venda;
    }

    public void consolidaProdutosItens(Venda venda){
        venda.getListaItens().forEach( item -> {
            if(item.getProduto() != null && item.getProduto().getIdProduto() != null) {
                item.setProduto(produtoRepository.findOne(item.getProduto().getIdProduto()));
            }else{
                throw new RuntimeException("Lista de itens contem produto inválido");
            }
            if(item.getValor() == null)
                item.setValor(item.getQtde().multiply(item.getProduto().getValorVenda()));
        });
    }

    public void calculaValorFinal(Venda venda){
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
