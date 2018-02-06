package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class VendaMesaDTO extends BaseDTO {

    private Long idVendaMesa;
    private VendaDTO venda;
    private MesaDTO mesa;
    private MesaStatus status;

}
