package br.com.achimid.lanchonete.api.compra.vendaPagamento;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import br.com.achimid.lanchonete.api.compra.venda.Venda;
import br.com.achimid.lanchonete.api.formaPagamento.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "venda_pagamento")
@EqualsAndHashCode(of = "idVendaPagamento", callSuper=false)
public class VendaPagamento extends EntidadeBase{

    @Id
    @GeneratedValue
    private Long idVendaPagamento;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id_forma_pagamento")
    @NotNull(message = "Forma de pagamento não pode ser nullo no pagamento")
    private FormaPagamento formaPagamento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "venda_id_venda")
    @NotNull(message = "Venda não pode ser nullo no pagamento")
    private Venda venda;

    @NotNull(message = "Valor não pode ser nullo no pagamento")
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valor;

}
