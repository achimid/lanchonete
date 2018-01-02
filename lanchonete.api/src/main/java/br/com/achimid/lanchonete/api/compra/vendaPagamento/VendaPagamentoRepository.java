package br.com.achimid.lanchonete.api.compra.vendaPagamento;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaPagamentoRepository extends CrudRepository<VendaPagamento, Long> {

}
