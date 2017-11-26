package br.com.achimid.lanchonete.api.venda;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaItemRepository extends CrudRepository<VendaItem, Long> {

}
