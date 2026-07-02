package br.com.ifba.prg04pizzly.avaliacoes.service;

import br.com.ifba.prg04pizzly.avaliacoes.dto.AvaliacaoRequestDTO;
import br.com.ifba.prg04pizzly.avaliacoes.dto.AvaliacaoResponseDTO;
import br.com.ifba.prg04pizzly.avaliacoes.entity.Avaliacao;
import br.com.ifba.prg04pizzly.avaliacoes.repository.AvaliacaoRepository;
import br.com.ifba.prg04pizzly.clientes.repository.ClienteRepository;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import br.com.ifba.prg04pizzly.pedidos.repository.PedidoRepository;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

//Implementação das regras de negócio de Avaliação
@Service
@RequiredArgsConstructor
public class AvaliacaoService implements AvaliacaoIService {

    // repositório responsável pela persistência das avaliações
    private final AvaliacaoRepository avaliacaoRepository;

    // repositório responsável pela busca dos clientes vinculados às avaliações
    private final ClienteRepository clienteRepository;

    // repositório responsável pela busca dos pedidos vinculados às avaliações
    private final PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public AvaliacaoResponseDTO save(AvaliacaoRequestDTO avaliacaoDTO) {

        // busca o cliente responsável pela avaliação
        Cliente cliente = clienteRepository.findById(avaliacaoDTO.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // busca o pedido que será avaliado
        Pedido pedido = pedidoRepository.findById(avaliacaoDTO.getPedidoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        // garante que o mesmo pedido não seja avaliado mais de uma vez
        if (avaliacaoRepository.existsByPedidoId(avaliacaoDTO.getPedidoId())) {
            throw new BusinessException("Este pedido já possui avaliação cadastrada");
        }

        // cria a avaliação com a data atual e os vínculos de cliente e pedido
        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setComentario(avaliacaoDTO.getComentario());
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        avaliacao.setCliente(cliente);
        avaliacao.setPedido(pedido);

        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        return converterParaResponseDTO(salva);
    }

    @Override
    public Page<AvaliacaoResponseDTO> findAll(Pageable pageable) {

        // busca todas as avaliações de forma paginada e converte para DTO de resposta
        return avaliacaoRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public AvaliacaoResponseDTO findById(Long id) {

        // busca a avaliação pelo id ou lança exceção caso não exista
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Avaliação não encontrada")); //caso não for encontrada

        return converterParaResponseDTO(avaliacao);
    }

    // busca todas as avaliações feitas pelo cliente informado
    @Override
    public List<AvaliacaoResponseDTO> findByClienteId(Long clienteId) {

        return avaliacaoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    @Override
    public AvaliacaoResponseDTO findByPedidoId(Long pedidoId) {

        // busca a avaliação vinculada ao pedido informado
        Avaliacao avaliacao = avaliacaoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Avaliação não encontrada para este pedido")); //caso não encontre

        return converterParaResponseDTO(avaliacao);
    }

    @Override
    @Transactional
    public AvaliacaoResponseDTO update(
            Long id,
            AvaliacaoRequestDTO avaliacaoDTO) {

        // busca a avaliação que será atualizada
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Avaliação não encontrada"));

        // busca o cliente que ficará vinculado à avaliação
        Cliente cliente = clienteRepository.findById(avaliacaoDTO.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // busca o pedido que ficará vinculado à avaliação
        Pedido pedido = pedidoRepository.findById(avaliacaoDTO.getPedidoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        // impede vincular a avaliação a outro pedido que já possui avaliação
        if (!avaliacao.getPedido().getId().equals(avaliacaoDTO.getPedidoId())
                && avaliacaoRepository.existsByPedidoId(avaliacaoDTO.getPedidoId())) {
            throw new BusinessException("Este pedido já possui avaliação cadastrada");
        }

        // atualiza os dados da avaliação
        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setComentario(avaliacaoDTO.getComentario());
        avaliacao.setCliente(cliente);
        avaliacao.setPedido(pedido);

        Avaliacao atualizada = avaliacaoRepository.save(avaliacao);

        return converterParaResponseDTO(atualizada);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        // busca a avaliação antes de remover para garantir que ela existe
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacaoRepository.delete(avaliacao);
    }

    //converte entidade para DTO de resposta
    private AvaliacaoResponseDTO converterParaResponseDTO(
            Avaliacao avaliacao) {

        AvaliacaoResponseDTO dto = new AvaliacaoResponseDTO();

        dto.setId(avaliacao.getId());
        dto.setNota(avaliacao.getNota());
        dto.setComentario(avaliacao.getComentario());
        dto.setDataAvaliacao(avaliacao.getDataAvaliacao());

        // adiciona os dados do cliente e do pedido vinculados à avaliação
        dto.setClienteId(avaliacao.getCliente().getId());
        dto.setClienteNome(avaliacao.getCliente().getNome());

        dto.setPedidoId(avaliacao.getPedido().getId());

        return dto;
    }
}