package br.com.ifba.prg04pizzly.notificacoes.service;

import br.com.ifba.prg04pizzly.notificacoes.dto.NotificacaoRequestDTO;
import br.com.ifba.prg04pizzly.notificacoes.dto.NotificacaoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//Interface responsável pelos serviços de notificação
public interface NotificacaoIService {

    // cadastra uma nova notificação
    NotificacaoResponseDTO save(NotificacaoRequestDTO notificacaoDTO);

    // listar notificações com paginação
    Page<NotificacaoResponseDTO> findAll(Pageable pageable);

    // buscar notificação pelo id
    NotificacaoResponseDTO findById(Long id);

    // listar todas notificações de um cliente
    List<NotificacaoResponseDTO> findByClienteId(Long clienteId);

    // listar apenas as notificações não lidas de um cliente
    List<NotificacaoResponseDTO> findNaoLidasByClienteId(Long clienteId);

    // marcar notificação como lida
    NotificacaoResponseDTO marcarComoLida(Long id);

    // atualizar os dados de uma notificação
    NotificacaoResponseDTO update(Long id, NotificacaoRequestDTO notificacaoDTO);


    // remove uma notificacao pelo id
    void delete(Long id);

    // cria uma notificação automática para um cliente
    NotificacaoResponseDTO criarAutomatica(
            Long clienteId,
            String titulo,
            String mensagem);

    // marca todas as notificações de um cliente como lidas (no front terá esse botão)
    void marcarTodasComoLidas(Long clienteId);
}