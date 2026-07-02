package br.com.ifba.prg04pizzly.notificacoes.service;

import br.com.ifba.prg04pizzly.clientes.repository.ClienteRepository;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.notificacoes.dto.NotificacaoRequestDTO;
import br.com.ifba.prg04pizzly.notificacoes.dto.NotificacaoResponseDTO;
import br.com.ifba.prg04pizzly.notificacoes.entity.Notificacao;
import br.com.ifba.prg04pizzly.notificacoes.repository.NotificacaoRepository;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

//implementação das regras de negócio de notificação
@Service
@RequiredArgsConstructor
public class NotificacaoService implements NotificacaoIService {

    // repositório responsável pela persistência das notificações
    private final NotificacaoRepository notificacaoRepository;

    // repositório responsável pela busca dos clientes vinculados às notificações
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public NotificacaoResponseDTO save(NotificacaoRequestDTO notificacaoDTO) {

        // busca o cliente que receberá a notificação
        Cliente cliente = clienteRepository.findById(notificacaoDTO.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // cria a notificação com data atual e status inicial como não lida
        Notificacao notificacao = new Notificacao();

        notificacao.setTitulo(notificacaoDTO.getTitulo());
        notificacao.setMensagem(notificacaoDTO.getMensagem());
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacao.setLida(false);
        notificacao.setCliente(cliente);

        Notificacao salva = notificacaoRepository.save(notificacao);

        return converterParaResponseDTO(salva);
    }

    @Override
    public Page<NotificacaoResponseDTO> findAll(Pageable pageable) {

        // busca todas as notificações de forma paginada e converte para DTO de resposta
        return notificacaoRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public NotificacaoResponseDTO findById(Long id) {

        // busca a notificação pelo id ou lança exceção caso não exista
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notificação não encontrada"));

        return converterParaResponseDTO(notificacao);
    }

    @Override
    public List<NotificacaoResponseDTO> findByClienteId(Long clienteId) {

        // busca todas as notificações vinculadas ao cliente informado
        return notificacaoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    @Override
    public List<NotificacaoResponseDTO> findNaoLidasByClienteId(Long clienteId) {

        // busca apenas as notificações ainda não lidas do cliente informado
        return notificacaoRepository.findByClienteIdAndLidaFalse(clienteId)
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public NotificacaoResponseDTO marcarComoLida(Long id) {

        // busca a notificação antes de alterar seu status de leitura
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notificação não encontrada"));

        // marca a notificação como lida
        notificacao.setLida(true);

        Notificacao atualizada = notificacaoRepository.save(notificacao);

        return converterParaResponseDTO(atualizada);
    }

    @Override
    @Transactional
    public NotificacaoResponseDTO update(
            Long id,
            NotificacaoRequestDTO notificacaoDTO) {

        // busca a notificação que será atualizada
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notificação não encontrada"));

        // busca o cliente que ficará vinculado à notificação
        Cliente cliente = clienteRepository.findById(notificacaoDTO.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        notificacao.setTitulo(notificacaoDTO.getTitulo());
        notificacao.setMensagem(notificacaoDTO.getMensagem());
        notificacao.setCliente(cliente);

        // atualiza os dados da notificação
        Notificacao atualizada = notificacaoRepository.save(notificacao);

        return converterParaResponseDTO(atualizada);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        // busca a notificação antes de remover para garantir que ela existe
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notificação não encontrada"));

        notificacaoRepository.delete(notificacao);
    }

    //cria uma notificação automática para um cliente
    @Override
    @Transactional
    public NotificacaoResponseDTO criarAutomatica(
            Long clienteId,
            String titulo,
            String mensagem) {

        // busca o cliente que receberá a notificação automática
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // cria a notificação automática como não lida
        Notificacao notificacao = new Notificacao();

        notificacao.setTitulo(titulo);
        notificacao.setMensagem(mensagem);
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacao.setLida(false);
        notificacao.setCliente(cliente);

        Notificacao salva = notificacaoRepository.save(notificacao);

        return converterParaResponseDTO(salva);
    }

    //marca todas as notificações não lidas de um cliente como lidas
    @Override
    @Transactional
    public void marcarTodasComoLidas(Long clienteId) {

        // busca todas as notificações não lidas do cliente
        List<Notificacao> notificacoes =
                notificacaoRepository.findByClienteIdAndLidaFalse(clienteId);

        // marca todas as notificações encontradas como lidas
        notificacoes.forEach(notificacao ->
                notificacao.setLida(true));

        notificacaoRepository.saveAll(notificacoes);
    }

    //converte entidade para DTO de resposta
    private NotificacaoResponseDTO converterParaResponseDTO(
            Notificacao notificacao) {

        NotificacaoResponseDTO dto = new NotificacaoResponseDTO();

        dto.setId(notificacao.getId());
        dto.setTitulo(notificacao.getTitulo());
        dto.setMensagem(notificacao.getMensagem());
        dto.setDataEnvio(notificacao.getDataEnvio());
        dto.setLida(notificacao.getLida());

        // adiciona os dados do cliente vinculado à notificação
        dto.setClienteId(notificacao.getCliente().getId());
        dto.setClienteNome(notificacao.getCliente().getNome());

        return dto;
    }
}