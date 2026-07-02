package br.com.ifba.prg04pizzly.itenspedido.service;

import br.com.ifba.prg04pizzly.itenspedido.dto.ItemPedidoRequestDTO;
import br.com.ifba.prg04pizzly.itenspedido.dto.ItemPedidoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//Interface responsável pelos serviços de item do pedido
public interface ItemPedidoIService {

    // cadastra um novo item no pedido
    ItemPedidoResponseDTO save(ItemPedidoRequestDTO itemDTO);

    // listar itens com paginação
    Page<ItemPedidoResponseDTO> findAll(Pageable pageable);

    // buscar item pelo id
    ItemPedidoResponseDTO findById(Long id);

    // listar todos os itens de um pedido
    List<ItemPedidoResponseDTO> findByPedidoId(Long pedidoId);

    // atualiza os dados de um item do pedido
    ItemPedidoResponseDTO update(Long id, ItemPedidoRequestDTO itemDTO);

    // remove um item pelo id
    void delete(Long id);
}