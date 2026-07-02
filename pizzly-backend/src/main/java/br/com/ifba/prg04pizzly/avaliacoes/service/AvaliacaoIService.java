package br.com.ifba.prg04pizzly.avaliacoes.service;

import br.com.ifba.prg04pizzly.avaliacoes.dto.AvaliacaoRequestDTO;
import br.com.ifba.prg04pizzly.avaliacoes.dto.AvaliacaoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//Interface responsável pelos serviços de avaliação
public interface AvaliacaoIService {

    // cadastra uma nova avaliação
    AvaliacaoResponseDTO save(AvaliacaoRequestDTO avaliacaoDTO);

    // listar avaliações com paginação
    Page<AvaliacaoResponseDTO> findAll(Pageable pageable);

    // buscar avaliação pelo id
    AvaliacaoResponseDTO findById(Long id);

    // buscar avaliação vinculada a um pedido
    AvaliacaoResponseDTO findByPedidoId(Long pedidoId);

    // listar todas as avaliações de um cliente
    List<AvaliacaoResponseDTO> findByClienteId(Long clienteId);

    // atualizar os dados de uma avaliação
    AvaliacaoResponseDTO update(Long id, AvaliacaoRequestDTO avaliacaoDTO);

    // remove uma avaliação pelo id
    void delete(Long id);
}