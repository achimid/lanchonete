package br.com.achimid.lanchonete.root.dto;

import br.com.achimid.lanchonete.root.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProdutoDTO extends BaseDTO{

    private Long idProduto;
    private String nome;
    private String descricao;
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valorVenda;
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valorCusto;
    private CategoriaDTO categoria;
    private String urlImg;

}
