package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class VendaDTO extends BaseDTO{

    private Long idVenda;
    private Date dataVenda;
    private BigDecimal valorFinal;
    private List<VendaItemDTO> listaItens;
    private List<VendaPagamentoDTO> pagamentos;

}
