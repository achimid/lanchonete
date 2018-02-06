package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class VendaDTO extends BaseDTO{

    private Long idVenda;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private Date dataVenda;
    private BigDecimal valorFinal;
    private List<VendaItemDTO> listaItens;
    private List<VendaPagamentoDTO> pagamentos;

}
