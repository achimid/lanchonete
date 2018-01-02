package br.com.achimid.lanchonete.api.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll(){
        return (List<Categoria>) categoriaRepository.findAll();
    }

    public Categoria findOne(Long id) {
        return categoriaRepository.findOne(id);
    }

    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    public void delete(Long id) {
        if (categoriaRepository.exists(id)) {
            categoriaRepository.delete(id);
        } else {
            throw new RuntimeException("Categoria não encontrada");
        }
    }

    public Boolean exists(Long id){
        return categoriaRepository.exists(id);
    }
}
