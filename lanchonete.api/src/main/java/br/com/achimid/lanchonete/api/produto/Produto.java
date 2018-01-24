package br.com.achimid.lanchonete.api.produto;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import br.com.achimid.lanchonete.api.categoria.Categoria;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;


import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produto")
@EqualsAndHashCode(of = "idProduto", callSuper = false)
public class Produto extends EntidadeBase {

    @Id
    @GeneratedValue
    private Long idProduto;

    @NotBlank(message = "Informe o nome")
    @NotNull(message = "O nome não pode ser nullo")
    private String nome;

    private String descricao;

    @NotNull(message = "Informe o valor de venda")
    @DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
    @DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valorVenda;

    @DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
    @DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valorCusto;

    @ManyToOne
    @JoinColumn(name = "categoria_id_categoria")
    @NotNull(message = "A categoria não pode ser nulla")
    private Categoria categoria;

    private String urlImg;


}
