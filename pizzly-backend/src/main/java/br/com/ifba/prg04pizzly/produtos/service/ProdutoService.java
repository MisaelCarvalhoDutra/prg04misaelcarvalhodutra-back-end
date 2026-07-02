package br.com.ifba.prg04pizzly.produtos.service;

import br.com.ifba.prg04pizzly.categorias.entity.Categoria;
import br.com.ifba.prg04pizzly.categorias.repository.CategoriaRepository;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.produtos.dto.ProdutoRequestDTO;
import br.com.ifba.prg04pizzly.produtos.dto.ProdutoResponseDTO;
import br.com.ifba.prg04pizzly.produtos.entity.Produto;
import br.com.ifba.prg04pizzly.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ifba.prg04pizzly.logs.service.LogAuditoriaIService;

import java.util.List;

//Implementação das regras de negócio da entidade produto
@Service
@RequiredArgsConstructor
public class ProdutoService implements ProdutoIService {

    // repositório responsável pela persistência dos produtos
    private final ProdutoRepository produtoRepository;

    // repositório responsável pela persistência das categorias
    private final CategoriaRepository categoriaRepository;

    // service responsável pelo registro de logs administrativos
    private final LogAuditoriaIService logAuditoriaService;

    @Override
    @Transactional
    public ProdutoResponseDTO save(
            ProdutoRequestDTO produtoDTO,
            Long funcionarioId) {

        // valida se os dados foram informados
        if (produtoDTO == null) {
            throw new BusinessException("Dados do produto não preenchidos");
        }

        // impede cadastro de produto com nome já existente
        if (produtoRepository.existsByNome(produtoDTO.getNome())) {
            throw new BusinessException(
                    "Produto já cadastrado");
        }

        // busca a categoria vinculada ao produto
        Categoria categoria = categoriaRepository
                .findById(produtoDTO.getCategoriaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada"));

        // cria a entidade e preenche os dados recebidos do DTO
        Produto produto = new Produto();

        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setImagem(produtoDTO.getImagem());
        produto.setDisponivel(produtoDTO.getDisponivel());
        produto.setCategoria(categoria);

        Produto salvo = produtoRepository.save(produto);

        // registra a criação do produto no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "CRIAR",
                "PRODUTO",
                salvo.getId(),
                "Produto " + salvo.getNome() + " cadastrado"
        );

        return converterParaResponseDTO(salvo);
    }

    @Override
    public Page<ProdutoResponseDTO> findAll(
            Pageable pageable) {

        // busca todos os produtos de forma paginada e converte para DTO de resposta
        return produtoRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public ProdutoResponseDTO findById(Long id) {

        // busca o produto pelo id ou lança exceção caso não exista
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado")); //exceção q será lançada

        return converterParaResponseDTO(produto);
    }

    @Override
    public List<ProdutoResponseDTO> findByCategoriaId(
            Long categoriaId) {

        // busca todos os produtos pertencentes à categoria informada
        return produtoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(
            Long id,
            ProdutoRequestDTO produtoDTO,
            Long funcionarioId) {

        // valida se os dados foram informados
        if (produtoDTO == null) {
            throw new BusinessException("Dados do produto não preenchidos");
        }

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado"));

        // impede atualizar o produto para um nome já utilizado por outro produto
        if (produtoRepository.existsByNomeAndIdNot(
                produtoDTO.getNome(), id)) {

            throw new BusinessException("Produto já cadastrado");
        }

        // busca a categoria vinculada ao produto
        Categoria categoria = categoriaRepository
                .findById(produtoDTO.getCategoriaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada"));

        // atualiza os dados do produto
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setImagem(produtoDTO.getImagem());
        produto.setDisponivel(produtoDTO.getDisponivel());
        produto.setCategoria(categoria);

        Produto atualizado = produtoRepository.save(produto);

        // registra a atualização do produto no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EDITAR",
                "PRODUTO",
                atualizado.getId(),
                "Produto " + atualizado.getNome() + " atualizado"
        );

        return converterParaResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public void delete(
            Long id,
            Long funcionarioId) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado"));

        // guarda os dados antes da exclusão para registrar corretamente na auditoria
        String nomeProduto = produto.getNome();
        Long produtoId = produto.getId();

        produtoRepository.delete(produto);

        // registra a remoção do produto no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EXCLUIR",
                "PRODUTO",
                produtoId,
                "Produto " + nomeProduto + " removido"
        );
    }

    //converte entidade para DTO de resposta
    private ProdutoResponseDTO converterParaResponseDTO(
            Produto produto) {

        ProdutoResponseDTO dto = new ProdutoResponseDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setImagem(produto.getImagem());
        dto.setDisponivel(produto.getDisponivel());

        // adiciona os dados da categoria vinculada ao produto
        dto.setCategoriaId(produto.getCategoria().getId());
        dto.setCategoriaNome(produto.getCategoria().getNome());

        return dto;
    }
}