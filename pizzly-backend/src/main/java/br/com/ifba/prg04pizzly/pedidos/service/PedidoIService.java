package br.com.ifba.prg04pizzly.pedidos.service;

import br.com.ifba.prg04pizzly.pedidos.dto.PedidoRequestDTO;
import br.com.ifba.prg04pizzly.pedidos.dto.PedidoResponseDTO;
import br.com.ifba.prg04pizzly.pedidos.entity.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//Interface responsável pelos serviços da entidade pedido
public interface PedidoIService {

    // cadastra um novo pedido
    PedidoResponseDTO save(PedidoRequestDTO pedidoDTO);

    // listar pedidos com paginação
    Page<PedidoResponseDTO> findAll(Pageable pageable);

    // buscar pedido pelo id
    PedidoResponseDTO findById(Long id);

    // listar todos os pedidos de um cliente
    List<PedidoResponseDTO> findByClienteId(Long clienteId);

    // atualiza os dados de um pedido
    PedidoResponseDTO update(Long id, PedidoRequestDTO pedidoDTO);

    // atualiza o status do pedido e registra o funcionário responsável
    PedidoResponseDTO updateStatus(
            Long id,
            StatusPedido status,
            Long funcionarioId);

    // remove um pedido pelo id
    void delete(Long id);
}