package br.com.achimid.lanchonete.api.formaPagamento;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends CrudRepository<FormaPagamento, Long> {
}
