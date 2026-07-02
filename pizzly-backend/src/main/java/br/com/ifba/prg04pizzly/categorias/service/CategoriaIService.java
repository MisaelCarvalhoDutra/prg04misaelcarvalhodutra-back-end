package br.com.ifba.prg04pizzly.categorias.service;

import br.com.ifba.prg04pizzly.categorias.dto.CategoriaRequestDTO;
import br.com.ifba.prg04pizzly.categorias.dto.CategoriaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Interface responsável pelos serviços de categoria
public interface CategoriaIService {

    // cadastra uma nova categoria e registra o funcionário responsável
    CategoriaResponseDTO save(
            CategoriaRequestDTO categoriaDTO,
            Long funcionarioId);

    // lista categorias com paginação
    Page<CategoriaResponseDTO> findAll(
            Pageable pageable);

    // busca uma categoria pelo id
    CategoriaResponseDTO findById(
            Long id);

    // atualiza uma categoria e registra o funcionário responsável
    CategoriaResponseDTO update(
            Long id,
            CategoriaRequestDTO categoriaDTO,
            Long funcionarioId);

    // remove uma categoria e registra o funcionário responsável
    void delete(
            Long id,
            Long funcionarioId);
}