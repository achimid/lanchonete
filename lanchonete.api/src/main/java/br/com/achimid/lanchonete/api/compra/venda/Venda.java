package br.com.achimid.lanchonete.api.compra.venda;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import br.com.achimid.lanchonete.api.compra.vendaItem.VendaItem;
import br.com.achimid.lanchonete.api.compra.vendaPagamento.VendaPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "venda")
@EqualsAndHashCode(of = "idVenda", callSuper=false)
public class Venda extends EntidadeBase{

    @Id
    @GeneratedValue
    private Long idVenda;

    @NotNull(message = "Data nao pode ser nulla")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private Date dataVenda;

    @NotNull(message = "Valor final não pode ser nullo")
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valorFinal;

    @NotNull(message = "A venda deve ter ao menos um item.")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVendaItem")
    private List<VendaItem> listaItens;

    //@NotNull(message = "A venda deve ter ao menos um pagamento.")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVendaPagamento")
    private List<VendaPagamento> pagamentos;

}
