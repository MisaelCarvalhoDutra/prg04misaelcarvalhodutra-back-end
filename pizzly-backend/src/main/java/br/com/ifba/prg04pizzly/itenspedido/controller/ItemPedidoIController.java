package br.com.ifba.prg04pizzly.itenspedido.controller;

import br.com.ifba.prg04pizzly.itenspedido.dto.ItemPedidoRequestDTO;
import br.com.ifba.prg04pizzly.itenspedido.dto.ItemPedidoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Interface dos endpoints de item do pedido, define o contrato das operações HTTP disponíveis para itens de pedido
public interface ItemPedidoIController {

    // cadastra um novo item no pedido
    ResponseEntity<ItemPedidoResponseDTO> save(
            ItemPedidoRequestDTO itemDTO);

    // listar itens com paginação
    ResponseEntity<Page<ItemPedidoResponseDTO>> findAll(
            Pageable pageable);

    // buscar item pelo id
    ResponseEntity<ItemPedidoResponseDTO> findById(
            Long id);

    // listar todos os itens de um pedido
    ResponseEntity<List<ItemPedidoResponseDTO>> findByPedidoId(
            Long pedidoId);

    // atualizar os dados de um item do pedido
    ResponseEntity<ItemPedidoResponseDTO> update(
            Long id,
            ItemPedidoRequestDTO itemDTO);

    //remove um item do pedido
    ResponseEntity<Void> delete(
            Long id);
}