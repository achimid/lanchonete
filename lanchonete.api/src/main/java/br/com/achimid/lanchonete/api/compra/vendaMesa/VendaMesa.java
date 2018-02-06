package br.com.achimid.lanchonete.api.compra.vendaMesa;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import br.com.achimid.lanchonete.api.compra.venda.Venda;
import br.com.achimid.lanchonete.api.mesa.Mesa;
import br.com.achimid.lanchonete.api.mesa.MesaStatus;
import br.com.achimid.lanchonete.api.produto.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "venda_mesa")
@EqualsAndHashCode(of = "idVendaMesa", callSuper=false)
public class VendaMesa extends EntidadeBase {

    @Id
    @GeneratedValue
    private Long idVendaMesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id_venda")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "mesa_id_mesa")
    private Mesa mesa;

    @Enumerated
    @NotNull(message = "status nao pode ser null")
    private MesaStatus status;

}
