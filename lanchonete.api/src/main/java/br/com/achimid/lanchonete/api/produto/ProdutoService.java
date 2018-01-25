package br.com.achimid.lanchonete.api.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll(){
        return (List<Produto>) produtoRepository.findAll();
    }

    public List<Produto> findByCategoria(Long idCategoria){
        return produtoRepository.findByCategoriaIdCategoria(idCategoria);
    }

    public Produto findOne(Long id) {
        return produtoRepository.findOne(id);
    }

    public void save(Produto produto) {
        produtoRepository.save(produto);
    }

    public void delete(Long id) {
        if (produtoRepository.exists(id)) {
            produtoRepository.delete(id);
        } else {
            throw new RuntimeException("Produto não encontrada");
        }
    }

    public Boolean exists(Long id){
        return produtoRepository.exists(id);
    }

}
