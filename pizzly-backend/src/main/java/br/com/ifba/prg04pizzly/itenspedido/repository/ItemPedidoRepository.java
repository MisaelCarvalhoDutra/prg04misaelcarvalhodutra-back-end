package br.com.ifba.prg04pizzly.itenspedido.repository;

import br.com.ifba.prg04pizzly.itenspedido.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//repositório responsável pelas operações de persistência da entidade ItemPedido.
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    // lista todos os itens pertencentes a um pedido específico
    List<ItemPedido> findByPedidoId(Long pedidoId);
}