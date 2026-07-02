package br.com.ifba.prg04pizzly.promocoes.repository;

import br.com.ifba.prg04pizzly.promocoes.entity.Promocao;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositório responsável pelas operações de persistência da entidade Promocao.
public interface PromocaoRepository extends JpaRepository<Promocao, Long> {

    // verifica se já existe uma promoção cadastrada com o título informado
    boolean existsByTitulo(String titulo);

    // verifica se já existe outra promoção com o mesmo título durante a atualização
    boolean existsByTituloAndIdNot(String titulo, Long id);
}