package br.com.ifba.prg04pizzly.pedidos.controller;

import br.com.ifba.prg04pizzly.pedidos.dto.PedidoRequestDTO;
import br.com.ifba.prg04pizzly.pedidos.dto.PedidoResponseDTO;
import br.com.ifba.prg04pizzly.pedidos.entity.enums.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Interface dos endpoints de Pedido, define o contrato das operações HTTP disponíveis para pedidos
public interface PedidoIController {

    // cadastrar um novo pedido
    ResponseEntity<PedidoResponseDTO> save(
            PedidoRequestDTO pedidoDTO);

    // listar pedidos com paginação
    ResponseEntity<Page<PedidoResponseDTO>> findAll(
            Pageable pageable);

    // buscar pedido pelo id
    ResponseEntity<PedidoResponseDTO> findById(
            Long id);

    // listar todos os pedidos de um cliente
    ResponseEntity<List<PedidoResponseDTO>> findByClienteId(
            Long clienteId);

    // atualizar os dados de um pedido
    ResponseEntity<PedidoResponseDTO> update(
            Long id,
            PedidoRequestDTO pedidoDTO);

    // atualizar status do pedido informando o funcionário responsável
    ResponseEntity<PedidoResponseDTO> updateStatus(
            Long id,
            StatusPedido status,
            Long funcionarioId);

    // remove um pedido pelo id
    ResponseEntity<Void> delete(
            Long id);
}