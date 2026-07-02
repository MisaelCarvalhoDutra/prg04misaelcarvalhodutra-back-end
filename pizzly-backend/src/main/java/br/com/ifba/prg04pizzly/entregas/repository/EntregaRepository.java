package br.com.ifba.prg04pizzly.entregas.repository;

import br.com.ifba.prg04pizzly.entregas.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Repositório responsável pelas operações de persistência da entidade Entrega
public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    // busca entrega associada a um pedido especifico
    Optional<Entrega> findByPedidoId(Long pedidoId);

    // verifica se já existe entrega  cadastrada para o pedido
    boolean existsByPedidoId(Long pedidoId);
}