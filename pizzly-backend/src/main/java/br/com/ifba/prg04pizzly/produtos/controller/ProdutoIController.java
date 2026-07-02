package br.com.ifba.prg04pizzly.produtos.controller;

import br.com.ifba.prg04pizzly.produtos.dto.ProdutoRequestDTO;
import br.com.ifba.prg04pizzly.produtos.dto.ProdutoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Interface dos endpoints de produto, define o contrato das operações HTTP disponíveis para produtos
public interface ProdutoIController {

    //cadastra um novo produto e informa o funcionário responsável pela ação
    ResponseEntity<ProdutoResponseDTO> save(
            ProdutoRequestDTO produtoDTO,
            Long funcionarioId);

    // listar produtos com paginação
    ResponseEntity<Page<ProdutoResponseDTO>> findAll(
            Pageable pageable);

    // buscar produto pelo id
    ResponseEntity<ProdutoResponseDTO> findById(
            Long id);

    // lista todos os produtos de uma categoria
    ResponseEntity<List<ProdutoResponseDTO>> findByCategoriaId(
            Long categoriaId);

    // atualiza um produto e informa o funcionário responsável pela ação
    ResponseEntity<ProdutoResponseDTO> update(
            Long id,
            ProdutoRequestDTO produtoDTO,
            Long funcionarioId);

    // remove um produto e informa o funcionário responsável pela ação
    ResponseEntity<Void> delete(
            Long id,
            Long funcionarioId);
}