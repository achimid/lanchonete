package br.com.achimid.lanchonete.api.venda;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import br.com.achimid.lanchonete.api.produto.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "venda_item")
@EqualsAndHashCode(of = "id")
public class VendaItem extends EntidadeBase {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "venda_id_venda")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "produto_id_produto")
    private Produto produto;

    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valor;

    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal qtde;

}
