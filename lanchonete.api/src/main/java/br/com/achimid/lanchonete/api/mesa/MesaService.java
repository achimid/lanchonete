package br.com.achimid.lanchonete.api.mesa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    public List<Mesa> findAll(){
        List<Mesa> mesas = (List<Mesa>) mesaRepository.findAll();
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
