package br.com.ifba.prg04pizzly.pagamentos.repository;

import br.com.ifba.prg04pizzly.pagamentos.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//repositório responsável pelas operações de persistência da entidade Pagamento.
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    // busca o pagamento associado a um pedido especifico
    Optional<Pagamento> findByPedidoId(Long pedidoId);

    // verifica se já existe pagamento cadastrado para o pedido informado
    boolean existsByPedidoId(Long pedidoId);
}