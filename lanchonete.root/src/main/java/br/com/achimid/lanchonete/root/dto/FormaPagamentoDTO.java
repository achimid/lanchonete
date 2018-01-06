package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FormaPagamentoDTO extends BaseDTO {

    private Long idFormaPagamento;
    private String nome;
    private Boolean ativo;

}
