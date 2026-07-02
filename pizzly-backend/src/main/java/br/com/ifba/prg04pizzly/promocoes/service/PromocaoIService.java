package br.com.ifba.prg04pizzly.promocoes.service;

import br.com.ifba.prg04pizzly.promocoes.dto.PromocaoRequestDTO;
import br.com.ifba.prg04pizzly.promocoes.dto.PromocaoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Interface responsável pelos serviços da entidade promoção
public interface PromocaoIService {

    // cadastra uma nova promoção e registra o funcionário responsável
    PromocaoResponseDTO save(
            PromocaoRequestDTO promocaoDTO,
            Long funcionarioId);

    // lista promoções com paginação
    Page<PromocaoResponseDTO> findAll(Pageable pageable);

    // buscar promoção pelo id
    PromocaoResponseDTO findById(Long id);

    // atualiza uma promoção e registra o funcionário responsável
    PromocaoResponseDTO update(
            Long id,
            PromocaoRequestDTO promocaoDTO,
            Long funcionarioId);

    // remove uma promoção e registra o funcionário responsável
    void delete(
            Long id,
            Long funcionarioId);
}