package br.com.ifba.prg04pizzly.promocoes.service;

import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.produtos.entity.Produto;
import br.com.ifba.prg04pizzly.produtos.repository.ProdutoRepository;
import br.com.ifba.prg04pizzly.promocoes.dto.PromocaoRequestDTO;
import br.com.ifba.prg04pizzly.promocoes.dto.PromocaoResponseDTO;
import br.com.ifba.prg04pizzly.promocoes.entity.Promocao;
import br.com.ifba.prg04pizzly.promocoes.repository.PromocaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ifba.prg04pizzly.logs.service.LogAuditoriaIService;

import java.util.List;

//implementação das regras de negócio da entidade Promoção
@Service
@RequiredArgsConstructor
public class PromocaoService implements PromocaoIService {

    // repositório responsável pela persistência das promoções
    private final PromocaoRepository promocaoRepository;

    // repositório responsável pela busca dos produtos vinculados à promoção
    private final ProdutoRepository produtoRepository;

    // service responsável pelo registro de logs administrativos
    private final LogAuditoriaIService logAuditoriaService;

    @Override
    @Transactional
    public PromocaoResponseDTO save(
            PromocaoRequestDTO promocaoDTO,
            Long funcionarioId) {

        // valida se os dados foram informados
        if (promocaoDTO == null) {
            throw new BusinessException("Dados da promoção não preenchidos");
        }

        // impede cadastro de promoção com título já existente
        if (promocaoRepository.existsByTitulo(promocaoDTO.getTitulo())) {
            throw new BusinessException("Já existe uma promoção com este título");
        }

        // busca os produtos que farão parte da promoção
        List<Produto> produtos = produtoRepository.findAllById(
                promocaoDTO.getProdutosIds()
        );

        if (produtos.size() != promocaoDTO.getProdutosIds().size()) {
            throw new ResourceNotFoundException("Um ou mais produtos não foram encontrados");
        }

        // cria a entidade e preenche os dados recebidos do DTO
        Promocao promocao = new Promocao();

        promocao.setTitulo(promocaoDTO.getTitulo());
        promocao.setDescricao(promocaoDTO.getDescricao());
        promocao.setPrecoPromocional(promocaoDTO.getPrecoPromocional());
        promocao.setDataInicio(promocaoDTO.getDataInicio());
        promocao.setDataFim(promocaoDTO.getDataFim());
        promocao.setAtiva(promocaoDTO.getAtiva());
        promocao.setProdutos(produtos);
        promocao.setTag(promocaoDTO.getTag());
        promocao.setImagem(promocaoDTO.getImagem());
        promocao.setPrecoAntigo(promocaoDTO.getPrecoAntigo());

        Promocao salva = promocaoRepository.save(promocao);

        // registra a criação da promoção no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "CRIAR",
                "PROMOCAO",
                salva.getId(),
                "Promoção " + salva.getTitulo() + " cadastrada"
        );

        return converterParaResponseDTO(salva);
    }

    @Override
    public Page<PromocaoResponseDTO> findAll(Pageable pageable) {

        // busca todas as promoções de forma paginada e converte para DTO de resposta
        return promocaoRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public PromocaoResponseDTO findById(Long id) {

        // busca a promoção pelo id ou lança exceção caso não exista
        Promocao promocao = promocaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Promoção não encontrada")); //caso n exista

        return converterParaResponseDTO(promocao);
    }

    @Override
    @Transactional
    public PromocaoResponseDTO update(
            Long id,
            PromocaoRequestDTO promocaoDTO,
            Long funcionarioId) {

        // valida se os dados foram informados
        if (promocaoDTO == null) {
            throw new BusinessException("Dados da promoção não preenchidos");
        }

        Promocao promocao = promocaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Promoção não encontrada"));

        // impede atualizar a promoção para um título já utilizado por outra promoção
        if (promocaoRepository.existsByTituloAndIdNot(
                promocaoDTO.getTitulo(), id)) {

            throw new BusinessException(
                    "Já existe uma promoção com este título");
        }

        // busca os produtos que farão parte da promoção
        List<Produto> produtos = produtoRepository.findAllById(
                promocaoDTO.getProdutosIds()
        );

        if (produtos.size() != promocaoDTO.getProdutosIds().size()) {
            throw new ResourceNotFoundException("Um ou mais produtos não foram encontrados");
        }

        // atualiza os dados da promoção
        promocao.setTitulo(promocaoDTO.getTitulo());
        promocao.setDescricao(promocaoDTO.getDescricao());
        promocao.setPrecoPromocional(promocaoDTO.getPrecoPromocional());
        promocao.setDataInicio(promocaoDTO.getDataInicio());
        promocao.setDataFim(promocaoDTO.getDataFim());
        promocao.setAtiva(promocaoDTO.getAtiva());
        promocao.setProdutos(produtos);
        promocao.setTag(promocaoDTO.getTag());
        promocao.setImagem(promocaoDTO.getImagem());
        promocao.setPrecoAntigo(promocaoDTO.getPrecoAntigo());

        Promocao atualizada = promocaoRepository.save(promocao);

        // registra a atualização da promoção no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EDITAR",
                "PROMOCAO",
                atualizada.getId(),
                "Promoção " + atualizada.getTitulo() + " atualizada"
        );

        return converterParaResponseDTO(atualizada);
    }

    @Override
    @Transactional
    public void delete(
            Long id,
            Long funcionarioId) {

        Promocao promocao = promocaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Promoção não encontrada"));

        // guarda os dados antes da exclusão para registrar corretamente na auditoria
        String tituloPromocao = promocao.getTitulo();
        Long promocaoId = promocao.getId();

        promocaoRepository.delete(promocao);

        // registra a remoção da promoção no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EXCLUIR",
                "PROMOCAO",
                promocaoId,
                "Promoção " + tituloPromocao + " removida"
        );
    }

    //converte entidade para DTO de resposta
    private PromocaoResponseDTO converterParaResponseDTO(
            Promocao promocao) {

        PromocaoResponseDTO dto = new PromocaoResponseDTO();

        dto.setId(promocao.getId());
        dto.setTitulo(promocao.getTitulo());
        dto.setDescricao(promocao.getDescricao());
        dto.setPrecoPromocional(promocao.getPrecoPromocional());
        dto.setDataInicio(promocao.getDataInicio());
        dto.setDataFim(promocao.getDataFim());
        dto.setAtiva(promocao.getAtiva());
        dto.setTag(promocao.getTag());
        dto.setImagem(promocao.getImagem());
        dto.setPrecoAntigo(promocao.getPrecoAntigo());

        // adiciona os ids dos produtos vinculados à promoção
        dto.setProdutosIds(
                promocao.getProdutos()
                        .stream()
                        .map(Produto::getId)
                        .toList()
        );

        // adiciona os nomes dos produtos vinculados à promoção
        dto.setProdutosNomes(
                promocao.getProdutos()
                        .stream()
                        .map(Produto::getNome)
                        .toList()
        );

        return dto;
    }
}