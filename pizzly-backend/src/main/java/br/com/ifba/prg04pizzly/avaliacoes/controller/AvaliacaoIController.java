package br.com.ifba.prg04pizzly.avaliacoes.controller;

import br.com.ifba.prg04pizzly.avaliacoes.dto.AvaliacaoRequestDTO;
import br.com.ifba.prg04pizzly.avaliacoes.dto.AvaliacaoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Interface dos endpoints da entidade Avaliação
public interface AvaliacaoIController {

    // cadastra uma nova avaliação
    ResponseEntity<AvaliacaoResponseDTO> save(
            AvaliacaoRequestDTO avaliacaoDTO);

    // lista avaliações com paginação
    ResponseEntity<Page<AvaliacaoResponseDTO>> findAll(
            Pageable pageable);

    // busca uma avaliação pelo id
    ResponseEntity<AvaliacaoResponseDTO> findById(
            Long id);

    // lista todas as avaliações feitas por um cliente
    ResponseEntity<List<AvaliacaoResponseDTO>> findByClienteId(
            Long clienteId);

    // busca a avaliação vinculada a um pedido
    ResponseEntity<AvaliacaoResponseDTO> findByPedidoId(
            Long pedidoId);

    // atualiza os dados de uma avaliação
    ResponseEntity<AvaliacaoResponseDTO> update(
            Long id,
            AvaliacaoRequestDTO avaliacaoDTO);

    // remove uma avaliação pelo id
    ResponseEntity<Void> delete(
            Long id);
}