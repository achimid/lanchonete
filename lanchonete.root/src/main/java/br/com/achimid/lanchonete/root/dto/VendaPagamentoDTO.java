package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class VendaPagamentoDTO extends BaseDTO{

    private Long idVendaPagamento;
    private FormaPagamentoDTO formaPagamento;
    private VendaDTO venda;
    private BigDecimal valor;

}
