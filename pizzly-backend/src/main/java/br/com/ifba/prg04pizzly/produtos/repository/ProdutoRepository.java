package br.com.ifba.prg04pizzly.produtos.repository;

import br.com.ifba.prg04pizzly.produtos.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Repositório responsável pelas operações de persistência de Produto.
public interface ProdutoRepository
        extends JpaRepository<Produto, Long> {

    // lista produtos de uma categoria
    List<Produto> findByCategoriaId(Long categoriaId);

    // valida nome duplicado
    boolean existsByNome(String nome);

    // verifica se já existe outro produto com o mesmo nome
    boolean existsByNomeAndIdNot(String nome, Long id);
}