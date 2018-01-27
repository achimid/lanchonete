package br.com.achimid.lanchonete.api.compra.vendaMesa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaMesaRepository extends CrudRepository<VendaMesa, Long> {

    VendaMesa findTop1ByMesaIdMesaOrderByIdVendaMesaDesc(Long idMesa);
}
