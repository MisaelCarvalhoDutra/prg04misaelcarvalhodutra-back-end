package br.com.ifba.prg04pizzly.pedidos.service;

import br.com.ifba.prg04pizzly.clientes.repository.ClienteRepository;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.pedidos.dto.PedidoRequestDTO;
import br.com.ifba.prg04pizzly.pedidos.dto.PedidoResponseDTO;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import br.com.ifba.prg04pizzly.pedidos.entity.enums.StatusPedido;
import br.com.ifba.prg04pizzly.pedidos.repository.PedidoRepository;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ifba.prg04pizzly.logs.service.LogAuditoriaIService;

import br.com.ifba.prg04pizzly.notificacoes.service.NotificacaoIService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//Implementação das regras de negócio da entidade pedido
@Service
@RequiredArgsConstructor
public class PedidoService implements PedidoIService {

    // repositório responsável pela persistência dos pedidos
    private final PedidoRepository pedidoRepository;

    // repositório responsável pela persistência dos clientes
    private final ClienteRepository clienteRepository;

    // service responsável pelo registro de logs administrativos
    private final LogAuditoriaIService logAuditoriaService;

    // service responsável pela criação de notificações para clientes
    private final NotificacaoIService notificacaoService;

    @Override
    @Transactional
    public PedidoResponseDTO save(PedidoRequestDTO pedidoDTO) {

        // valida se os dados foram informados
        if (pedidoDTO == null) {
            throw new ResourceNotFoundException("Dados do pedido não preenchidos");
        }

        // busca o cliente responsável pelo pedido
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // cria o pedido com os dados recebidos e define informações automáticas
        Pedido pedido = new Pedido();

        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setSubtotal(pedidoDTO.getSubtotal());
        pedido.setTaxaEntrega(pedidoDTO.getTaxaEntrega());
        pedido.setTotal(calcularTotal(
                pedidoDTO.getSubtotal(),
                pedidoDTO.getTaxaEntrega()));
        pedido.setObservacao(pedidoDTO.getObservacao());

        // salva os dados de recebimento escolhidos no front
        pedido.setFormaRecebimento(pedidoDTO.getFormaRecebimento());
        pedido.setEnderecoId(pedidoDTO.getEnderecoId());

        pedido.setCliente(cliente);

        Pedido salvo = pedidoRepository.save(pedido);

        return converterParaResponseDTO(salvo);
    }

    @Override
    public Page<PedidoResponseDTO> findAll(Pageable pageable) {

        // busca todos os pedidos de forma paginada e converte para DTO de resposta
        return pedidoRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {

        // busca o pedido pelo id ou lança exceção caso não exista
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        return converterParaResponseDTO(pedido);
    }

    @Override
    public List<PedidoResponseDTO> findByClienteId(Long clienteId) {

        // busca todos os pedidos realizados pelo cliente informado
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public PedidoResponseDTO update(
            Long id,
            PedidoRequestDTO pedidoDTO) {

        // busca o pedido que será atualizado
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        // busca o cliente responsável pelo pedido
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // atualiza os dados do pedido
        pedido.setSubtotal(pedidoDTO.getSubtotal());
        pedido.setTaxaEntrega(pedidoDTO.getTaxaEntrega());
        pedido.setTotal(calcularTotal(
                pedidoDTO.getSubtotal(),
                pedidoDTO.getTaxaEntrega()));
        pedido.setObservacao(pedidoDTO.getObservacao());

        pedido.setFormaRecebimento(pedidoDTO.getFormaRecebimento());
        pedido.setEnderecoId(pedidoDTO.getEnderecoId());
        pedido.setCliente(cliente);

        Pedido atualizado = pedidoRepository.save(pedido);

        return converterParaResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public PedidoResponseDTO updateStatus(
            Long id,
            StatusPedido status,
            Long funcionarioId) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        // guarda o status anterior para registrar a alteração na auditoria
        StatusPedido statusAnterior = pedido.getStatus();

        // atualiza o status do pedido
        pedido.setStatus(status);

        Pedido atualizado = pedidoRepository.save(pedido);

        // notifica o cliente sobre a alteração de status do pedido
        notificacaoService.criarAutomatica(
                atualizado.getCliente().getId(),
                gerarTituloNotificacao(status),
                gerarMensagemNotificacao(atualizado.getId(), status)
        );

        // registra a alteração de status do pedido no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "ALTERAR_STATUS",
                "PEDIDO",
                atualizado.getId(),
                "Pedido " + atualizado.getId()
                        + " alterado de " + statusAnterior
                        + " para " + status
        );

        return converterParaResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        // busca o pedido antes de remover para garantir que ele existe
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        pedidoRepository.delete(pedido);
    }

    //calcula o valor total do pedido.
    private BigDecimal calcularTotal(
            BigDecimal subtotal,
            BigDecimal taxaEntrega) {

        return subtotal.add(taxaEntrega);
    }

    //define o título da notificação de acordo com o novo status
    private String gerarTituloNotificacao(StatusPedido status) {
        return switch (status) {
            case CONFIRMADO -> "Pedido confirmado";
            case PREPARANDO -> "Pedido em preparo";
            case SAIU_PARA_ENTREGA -> "Pedido saiu para entrega";
            case ENTREGUE -> "Pedido entregue";
            case CANCELADO -> "Pedido cancelado";
        };
    }

    //define a mensagem da notificação de acordo com o novo status
    private String gerarMensagemNotificacao(
            Long pedidoId,
            StatusPedido status) {

        return switch (status) {
            case CONFIRMADO ->
                    "Seu pedido #" + pedidoId + " foi confirmado pela pizzaria.";

            case PREPARANDO ->
                    "Seu pedido #" + pedidoId + " está sendo preparado.";

            case SAIU_PARA_ENTREGA ->
                    "Seu pedido #" + pedidoId + " saiu para entrega.";

            case ENTREGUE ->
                    "Seu pedido #" + pedidoId + " foi entregue. Bom apetite!";

            case CANCELADO ->
                    "Seu pedido #" + pedidoId + " foi cancelado. Entre em contato com a pizzaria para mais informações.";
        };
    }

    //vai converter entidade para DTO de resposta
    private PedidoResponseDTO converterParaResponseDTO(Pedido pedido) {

        PedidoResponseDTO dto = new PedidoResponseDTO();

        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatus(pedido.getStatus());
        dto.setSubtotal(pedido.getSubtotal());
        dto.setTaxaEntrega(pedido.getTaxaEntrega());
        dto.setTotal(pedido.getTotal());
        dto.setObservacao(pedido.getObservacao());

        dto.setFormaRecebimento(pedido.getFormaRecebimento());
        dto.setEnderecoId(pedido.getEnderecoId());

        dto.setClienteId(pedido.getCliente().getId());
        dto.setClienteNome(pedido.getCliente().getNome());

        return dto;
    }
}