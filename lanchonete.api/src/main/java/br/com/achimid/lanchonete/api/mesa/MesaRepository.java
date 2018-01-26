package br.com.achimid.lanchonete.api.mesa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends CrudRepository<Mesa, Long> {

}
