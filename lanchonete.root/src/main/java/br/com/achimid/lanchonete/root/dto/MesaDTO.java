package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class MesaDTO extends BaseDTO{

    private Long idMesa;
    private String descricao;
    private MesaStatus status;

}

enum MesaStatus {
    LIVRE, OCUPADA;
}

