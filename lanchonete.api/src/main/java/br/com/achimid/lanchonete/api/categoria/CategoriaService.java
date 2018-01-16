package br.com.achimid.lanchonete.api.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> find(String nome, String descricao){
        if(nome == null && descricao == null){
            return findAll();
        }else{
            //Categoria ex = new Categoria();
            //ex.setNome(nome);
            //ex.setDescricao(descricao);

            //ExampleMatcher matcher = ExampleMatcher
            //        .matching()
            //        .withMatcher("nome", matcher1 -> matcher1.contains())
            //        .withMatcher("descricao", matcher1 -> matcher1.contains());

            //return (List<Categoria>) categoriaRepository.findAll(Example.of(ex, matcher));

            return categoriaRepository.findByNomeContainingOrDescricaoContains(nome, descricao);
        }

    }

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
            try {
                categoriaRepository.delete(id);
            }catch (Exception e){
                String erroMsg = e.getMessage();
                if(e instanceof DataIntegrityViolationException){
                    erroMsg = "Esta categoria não pode ser excluida pois esta em uso.";
                }else{
                    erroMsg = "Erro ao excluir categoria. ".concat(erroMsg);
                }
                throw new RuntimeException(erroMsg, e);
            }
        } else {
            throw new RuntimeException("Categoria não encontrada");
        }
    }
    public Boolean exists(Long id){
        return categoriaRepository.exists(id);
    }
}
