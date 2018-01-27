package br.com.achimid.lanchonete.api.mesa;

import br.com.achimid.lanchonete.api.compra.vendaMesa.VendaMesa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends CrudRepository<Mesa, Long> {



}
