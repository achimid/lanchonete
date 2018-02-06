package br.com.achimid.lanchonete.api.formaPagamento;

import br.com.achimid.lanchonete.api.base.EntidadeBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="forma_pagamento")
@EqualsAndHashCode(of = "idFormaPagamento", callSuper=false)
public class FormaPagamento extends EntidadeBase{

    public FormaPagamento(String nome) {
        this.nome = nome;
    }

    public FormaPagamento() {
    }

    @Id
    @GeneratedValue
    private Long idFormaPagamento;

    @NotNull(message = "Nome da forma de pagamento não pode ser nullo")
    @NotEmpty(message = "Nome da forma de pagamento não pode ser vazio")
    private String nome;

    private Boolean ativo;

    private String urlImg;

}
