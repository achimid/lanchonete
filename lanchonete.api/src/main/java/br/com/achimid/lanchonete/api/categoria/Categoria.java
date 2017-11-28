package br.com.achimid.lanchonete.api.categoria;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "categoria")
@EqualsAndHashCode(of = "idCategoria", callSuper = false)
public class Categoria extends EntidadeBase{

    @Id
    @GeneratedValue
    private Long idCategoria;

    @NotNull(message = "O nome não pode ser nullo.")
    @NotEmpty(message = "Informe o campo nome.")
    private String nome;

    private String descricao;

}
