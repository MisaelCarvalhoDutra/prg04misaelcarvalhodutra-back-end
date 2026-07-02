package br.com.ifba.prg04pizzly.categorias.service;

import br.com.ifba.prg04pizzly.categorias.dto.CategoriaRequestDTO;
import br.com.ifba.prg04pizzly.categorias.dto.CategoriaResponseDTO;
import br.com.ifba.prg04pizzly.categorias.entity.Categoria;
import br.com.ifba.prg04pizzly.categorias.repository.CategoriaRepository;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ifba.prg04pizzly.logs.service.LogAuditoriaIService;

//Implementação das regras de negócio de categoria
@Service
@RequiredArgsConstructor
public class CategoriaService implements CategoriaIService {

    // repositório responsável pela persistência das categorias
    private final CategoriaRepository categoriaRepository;

    // responsável pela conversão entre DTOs e entidades
    private final ModelMapper modelMapper;

    // service responsável pelo registro de logs administrativos
    private final LogAuditoriaIService logAuditoriaService;

    @Override
    @Transactional
    public CategoriaResponseDTO save(
            CategoriaRequestDTO categoriaDTO,
            Long funcionarioId) {

        // valida se os dados foram informados
        if (categoriaDTO == null) {
            throw new BusinessException("Dados da categoria não preenchidos");
        }

        // impede cadastro de categoria com nome já existente
        if (categoriaRepository.existsByNome(categoriaDTO.getNome())) {
            throw new BusinessException(
                    "Categoria já cadastrada");
        }

        // converte DTO para entidade
        Categoria categoria =
                modelMapper.map(categoriaDTO, Categoria.class);

        Categoria salva =
                categoriaRepository.save(categoria);

        // registra a criação da categoria no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "CRIAR",
                "CATEGORIA",
                salva.getId(),
                "Categoria " + salva.getNome() + " cadastrada"
        );

        return modelMapper.map(
                salva,
                CategoriaResponseDTO.class);
    }

    @Override
    public Page<CategoriaResponseDTO> findAll(
            Pageable pageable) {

        // busca todas as categorias de forma paginada e converte para DTO de resposta
        return categoriaRepository.findAll(pageable)
                .map(categoria ->
                        modelMapper.map(
                                categoria,
                                CategoriaResponseDTO.class));
    }

    @Override
    public CategoriaResponseDTO findById(Long id) {

        // busca categoria pelo id ou lança exceção caso não exista
        Categoria categoria =
                categoriaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoria não encontrada")); //lança essa exceção

        return modelMapper.map(
                categoria,
                CategoriaResponseDTO.class);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(
            Long id,
            CategoriaRequestDTO categoriaDTO,
            Long funcionarioId) {

        // valida se os dados foram informados
        if (categoriaDTO == null) {
            throw new BusinessException("Dados da categoria não preenchidos");
        }

        Categoria categoria =
                categoriaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoria não encontrada"));

        // impede atualizar a categoria para um nome já utilizado por outra categoria
        if (categoriaRepository.existsByNomeAndIdNot(
                categoriaDTO.getNome(), id)) {

            throw new BusinessException(
                    "Categoria já cadastrada"); //exceção
        }

        // atualiza os dados da categoria
        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());
        categoria.setIcon(categoriaDTO.getIcon());

        Categoria atualizada =
                categoriaRepository.save(categoria);

        // registra a atualização da categoria no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EDITAR",
                "CATEGORIA",
                atualizada.getId(),
                "Categoria " + atualizada.getNome() + " atualizada"
        );

        return modelMapper.map(
                atualizada,
                CategoriaResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(
            Long id,
            Long funcionarioId) {

        Categoria categoria =
                categoriaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Categoria não encontrada"));

        // guarda os dados antes da exclusão para registrar corretamente na auditoria
        String nomeCategoria = categoria.getNome();
        Long categoriaId = categoria.getId();

        categoriaRepository.delete(categoria);

        // registra a remoção da categoria no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EXCLUIR",
                "CATEGORIA",
                categoriaId,
                "Categoria " + nomeCategoria + " removida"
        );
    }
}