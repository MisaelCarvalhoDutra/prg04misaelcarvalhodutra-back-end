package br.com.ifba.prg04pizzly.produtos.service;

import br.com.ifba.prg04pizzly.produtos.dto.ProdutoRequestDTO;
import br.com.ifba.prg04pizzly.produtos.dto.ProdutoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//Interface responsável pelos serviços da entidade produto
public interface ProdutoIService {

    // cadastra um novo produto e registra o funcionário responsável
    ProdutoResponseDTO save(
            ProdutoRequestDTO produtoDTO,
            Long funcionarioId);

    // listar produtos com paginação
    Page<ProdutoResponseDTO> findAll(
            Pageable pageable);

    // buscar produto pelo id
    ProdutoResponseDTO findById(
            Long id);

    // lista todos os produtos de uma categoria atraves do id da categoria
    List<ProdutoResponseDTO> findByCategoriaId(
            Long categoriaId);

    // atualiza um produto e registra o funcionário responsável
    ProdutoResponseDTO update(
            Long id,
            ProdutoRequestDTO produtoDTO,
            Long funcionarioId);

    // remove um produto e registra o funcionário responsável
    void delete(
            Long id,
            Long funcionarioId);
}