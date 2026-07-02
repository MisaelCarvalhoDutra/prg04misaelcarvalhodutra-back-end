package br.com.ifba.prg04pizzly.categorias.controller;

import br.com.ifba.prg04pizzly.categorias.dto.CategoriaRequestDTO;
import br.com.ifba.prg04pizzly.categorias.dto.CategoriaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//Interface dos endpoints de categoria, define o contrato das operações HTTP disponíveis para categorias
public interface CategoriaIController {

    // cadastra uma nova categoria e informa quem realizou a ação
    ResponseEntity<CategoriaResponseDTO> save(
            CategoriaRequestDTO categoriaDTO,
            Long funcionarioId);

    // lista categorias com paginação
    ResponseEntity<Page<CategoriaResponseDTO>> findAll(
            Pageable pageable);

    // buscar categoria pelo id
    ResponseEntity<CategoriaResponseDTO> findById(
            Long id);

    // atualiza uma categoria e informa quem realizou a ação
    ResponseEntity<CategoriaResponseDTO> update(
            Long id,
            CategoriaRequestDTO categoriaDTO,
            Long funcionarioId);

    // deletar categoria informando o funcionário responsável pela ação
    ResponseEntity<Void> delete(
            Long id,
            Long funcionarioId);
}