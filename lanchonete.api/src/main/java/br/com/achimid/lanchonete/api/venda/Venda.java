package br.com.achimid.lanchonete.api.venda;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "venda")
@EqualsAndHashCode(of = "id")
public class Venda extends EntidadeBase{

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Data nao pode ser nulla")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataVenda;

    @NotNull(message = "Valor final não pode ser nullo")
    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal valorFinal;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "id")
    private List<VendaItem> listaItens;

}
