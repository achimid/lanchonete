package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class VendaItemDTO extends BaseDTO{

    private Long idVendaItem;
    private VendaDTO venda;
    private ProdutoDTO produto;
    private BigDecimal valor;
    private BigDecimal qtde;
    private String observacao;

}
