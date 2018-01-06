package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProdutoDTO extends BaseDTO{

    private Long idProduto;
    private String nome;
    private String descricao;
    private BigDecimal valorVenda;
    private BigDecimal valorCusto;
    private CategoriaDTO categoria;

}
