package br.com.ifba.prg04pizzly.avaliacoes.repository;

import br.com.ifba.prg04pizzly.avaliacoes.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

//repositório responsável pelas operações de persistência da entidade Avaliacao
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    // lista avaliações feitas por um cliente  especifico
    List<Avaliacao> findByClienteId(Long clienteId);

    // verifica se um pedido já possui avaliação cadastrada
    boolean existsByPedidoId(Long pedidoId);

    // busca a avaliação associada a um pedido específico
    Optional<Avaliacao> findByPedidoId(Long pedidoId);
}