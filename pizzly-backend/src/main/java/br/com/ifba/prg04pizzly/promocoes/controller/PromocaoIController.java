package br.com.ifba.prg04pizzly.promocoes.controller;

import br.com.ifba.prg04pizzly.promocoes.dto.PromocaoRequestDTO;
import br.com.ifba.prg04pizzly.promocoes.dto.PromocaoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//interface dos endpoints da entidade Promoção
public interface PromocaoIController {

    //cadastra uma nova promoção e informa quem realizou a ação o funcionário responsável pela ação
    ResponseEntity<PromocaoResponseDTO> save(
            PromocaoRequestDTO promocaoDTO,
            Long funcionarioId);

    // lista promoções com paginação
    ResponseEntity<Page<PromocaoResponseDTO>> findAll(
            Pageable pageable);

    // buscar promoção pelo id
    ResponseEntity<PromocaoResponseDTO> findById(
            Long id);

    // atualiza uma promoção e informa o funcionário responsável pela ação
    ResponseEntity<PromocaoResponseDTO> update(
            Long id,
            PromocaoRequestDTO promocaoDTO,
            Long funcionarioId);

    // remove uma promoção e informa o funcionário responsável pela ação
    ResponseEntity<Void> delete(
            Long id,
            Long funcionarioId);
}