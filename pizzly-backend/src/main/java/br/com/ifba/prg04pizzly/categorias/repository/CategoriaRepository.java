package br.com.ifba.prg04pizzly.categorias.repository;

import br.com.ifba.prg04pizzly.categorias.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositório responsável pela persistência da entidade Categoria
public interface CategoriaRepository
        extends JpaRepository<Categoria, Long> {

    // verifica se já existe uma categoria cadastrada com o nome informado
    boolean existsByNome(String nome);

    // verifica se já existe outra categoria com o mesmo nome
    boolean existsByNomeAndIdNot(String nome, Long id);
}