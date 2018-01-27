package br.com.achimid.lanchonete.api.mesa;

import br.com.achimid.lanchonete.api.compra.vendaMesa.VendaMesa;
import br.com.achimid.lanchonete.api.compra.vendaMesa.VendaMesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private VendaMesaRepository vendaMesaRepository;

    public List<Mesa> findAll(){
        List<Mesa> mesas = (List<Mesa>) mesaRepository.findAll();
        mesas.forEach(m -> {
            m.setStatus(MesaStatus.LIVRE.name().toLowerCase());
            VendaMesa last = vendaMesaRepository.findTop1ByMesaIdMesaOrderByIdVendaMesaDesc(m.getIdMesa());
            if(last != null)
                m.setStatus(last.getStatus().name().toLowerCase());
        });

        // alguma maneira de buscar os status da mesa;
        return mesas;
    }

    public Mesa findOne(Long id) {
        return mesaRepository.findOne(id);
    }

    public void save(Mesa mesa) {
        mesaRepository.save(mesa);
    }

    public void delete(Long id) {
        if (mesaRepository.exists(id)) {
            mesaRepository.delete(id);
        } else {
            throw new RuntimeException("Mesa não encontrada");
        }
    }

    public Boolean exists(Long id){
        return mesaRepository.exists(id);
    }

}
