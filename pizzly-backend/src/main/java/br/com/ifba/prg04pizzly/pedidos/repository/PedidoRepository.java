package br.com.ifba.prg04pizzly.pedidos.repository;

import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Repositório responsável pelas operações de persistência da entidade Pedido
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // lista todos os pedidos realizados por um cliente especifico
    List<Pedido> findByClienteId(Long clienteId);
}