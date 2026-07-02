package br.com.ifba.prg04pizzly.pagamentos.service;

import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.pagamentos.dto.PagamentoRequestDTO;
import br.com.ifba.prg04pizzly.pagamentos.dto.PagamentoResponseDTO;
import br.com.ifba.prg04pizzly.pagamentos.entity.Pagamento;
import br.com.ifba.prg04pizzly.pagamentos.entity.enums.StatusPagamento;
import br.com.ifba.prg04pizzly.pagamentos.repository.PagamentoRepository;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import br.com.ifba.prg04pizzly.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//regras de negócio da entidade pagamento
@Service
@RequiredArgsConstructor
public class PagamentoService implements PagamentoIService {

    // repositório responsável pela persistência dos pagamentos
    private final PagamentoRepository pagamentoRepository;

    // repositório responsável pela busca dos pedidos vinculados aos pagamentos
    private final PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public PagamentoResponseDTO save(PagamentoRequestDTO pagamentoDTO) {

        // busca o pedido relacionado ao pagamento
        Pedido pedido = pedidoRepository.findById(pagamentoDTO.getPedidoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        // garante que um pedido tenha apenas um pagamento
        if (pagamentoRepository.existsByPedidoId(pagamentoDTO.getPedidoId())) {
            throw new BusinessException("Este pedido já possui pagamento cadastrado");
        }

        // cria o pagamento e define o status inicial como pendente (todo pagamento nasce como PENDENTE)
        Pagamento pagamento = new Pagamento();

        pagamento.setFormaPagamento(pagamentoDTO.getFormaPagamento());
        pagamento.setValorTroco(pagamentoDTO.getValorTroco());
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        pagamento.setPedido(pedido);

        Pagamento salvo = pagamentoRepository.save(pagamento);

        return converterParaResponseDTO(salvo);
    }

    @Override
    public Page<PagamentoResponseDTO> findAll(Pageable pageable) {

        // busca todos os pagamentos de forma paginada e converte para DTO de resposta
        return pagamentoRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public PagamentoResponseDTO findById(Long id) {

        // busca o pagamento pelo id ou lança exceção caso não exista
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pagamento não encontrado")); //exceção caso n encontre

        return converterParaResponseDTO(pagamento);
    }

    @Override
    public PagamentoResponseDTO findByPedidoId(Long pedidoId) {

        // busca o pagamento vinculado ao pedido informado
        Pagamento pagamento = pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pagamento não encontrado para este pedido"));

        return converterParaResponseDTO(pagamento);
    }

    @Override
    @Transactional
    public PagamentoResponseDTO update(
            Long id,
            PagamentoRequestDTO pagamentoDTO) {

        // busca o pagamento que será atualizado
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pagamento não encontrado"));

        // busca o pedido que ficará vinculado ao pagamento
        Pedido pedido = pedidoRepository.findById(pagamentoDTO.getPedidoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        // impede vincular o pagamento a outro pedido que já possui pagamento
        if (!pagamento.getPedido().getId().equals(pagamentoDTO.getPedidoId())
                && pagamentoRepository.existsByPedidoId(pagamentoDTO.getPedidoId())) {
            throw new BusinessException("Este pedido já possui pagamento cadastrado");
        }

        // atualiza os dados do pagamento
        pagamento.setFormaPagamento(pagamentoDTO.getFormaPagamento());
        pagamento.setValorTroco(pagamentoDTO.getValorTroco());
        pagamento.setPedido(pedido);

        Pagamento atualizado = pagamentoRepository.save(pagamento);

        return converterParaResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public PagamentoResponseDTO updateStatus(
            Long id,
            StatusPagamento statusPagamento) {

        // busca o pagamento antes de alterar seu status
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pagamento não encontrado"));

        // atualiza apenas o status do pagamento
        pagamento.setStatusPagamento(statusPagamento);

        Pagamento atualizado = pagamentoRepository.save(pagamento);

        return converterParaResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        // busca o pagamento antes de remover para garantir que ele existe
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pagamento não encontrado"));

        pagamentoRepository.delete(pagamento);
    }

    //converte entidade para DTO de resposta
    private PagamentoResponseDTO converterParaResponseDTO(Pagamento pagamento) {

        PagamentoResponseDTO dto = new PagamentoResponseDTO();

        dto.setId(pagamento.getId());
        dto.setFormaPagamento(pagamento.getFormaPagamento());
        dto.setStatusPagamento(pagamento.getStatusPagamento());
        dto.setValorTroco(pagamento.getValorTroco());

        // adiciona o id do pedido vinculado ao pagamento
        dto.setPedidoId(pagamento.getPedido().getId());

        return dto;
    }
}