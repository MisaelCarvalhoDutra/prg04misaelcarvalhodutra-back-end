package br.com.ifba.prg04pizzly.notificacoes.controller;

import br.com.ifba.prg04pizzly.notificacoes.dto.NotificacaoRequestDTO;
import br.com.ifba.prg04pizzly.notificacoes.dto.NotificacaoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Interface dos endpoints da entidade notificação
public interface NotificacaoIController {

    // cadastra uma nova notificação
    ResponseEntity<NotificacaoResponseDTO> save(
            NotificacaoRequestDTO notificacaoDTO);

    // lista notificações com paginação
    ResponseEntity<Page<NotificacaoResponseDTO>> findAll(
            Pageable pageable);

    // busca uma notificação pelo id
    ResponseEntity<NotificacaoResponseDTO> findById(
            Long id);

    // lista todas as notificações de um cliente
    ResponseEntity<List<NotificacaoResponseDTO>> findByClienteId(
            Long clienteId);

    // lista apenas as notificações não lidas de um cliente
    ResponseEntity<List<NotificacaoResponseDTO>> findNaoLidasByClienteId(
            Long clienteId);

    // marca uma notificação como lida
    ResponseEntity<NotificacaoResponseDTO> marcarComoLida(
            Long id);

    // marca todas as notificações de um cliente como lidas
    ResponseEntity<Void> marcarTodasComoLidas(
            Long clienteId);

    // atualiza os dados de uma notificação
    ResponseEntity<NotificacaoResponseDTO> update(
            Long id,
            NotificacaoRequestDTO notificacaoDTO);

    // remove uma notificação pelo id
    ResponseEntity<Void> delete(
            Long id);
}