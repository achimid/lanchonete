package br.com.achimid.lanchonete.api.categoria;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
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

    @NotNull(message = "O nome não pode ser nullo")
    @NotEmpty(message = "Informe o Nome da categoria")
    @NotBlank(message = "O Nome da categoria nao pode ser branco")
    private String nome;

    private String descricao;

}
