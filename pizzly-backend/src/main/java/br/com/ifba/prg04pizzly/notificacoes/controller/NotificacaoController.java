package br.com.ifba.prg04pizzly.notificacoes.controller;

import br.com.ifba.prg04pizzly.notificacoes.dto.NotificacaoRequestDTO;
import br.com.ifba.prg04pizzly.notificacoes.dto.NotificacaoResponseDTO;
import br.com.ifba.prg04pizzly.notificacoes.service.NotificacaoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller responsável pelos endpoints de notificação
//ele recebe as requisições HTTP e delega as regras de negócio ao NotificacaoService
@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController implements NotificacaoIController {

    // service responsável pelas regras de negócio de notificação
    private final NotificacaoIService notificacaoService;

    //cadastra uma nova notificação.
    @Override
    @PostMapping
    public ResponseEntity<NotificacaoResponseDTO> save(
            @Valid @RequestBody NotificacaoRequestDTO notificacaoDTO) {

        // cadastra a notificação informada
        NotificacaoResponseDTO novaNotificacao =
                notificacaoService.save(notificacaoDTO);

        return ResponseEntity.status(201).body(novaNotificacao);
    }

    //Lista todas as notificações
    @Override
    @GetMapping
    public ResponseEntity<Page<NotificacaoResponseDTO>> findAll(
            Pageable pageable) {

        // busca todas as notificações cadastradas
        Page<NotificacaoResponseDTO> notificacoes =
                notificacaoService.findAll(pageable);

        return ResponseEntity.ok(notificacoes);
    }

    //busca uma notificação pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> findById(
            @PathVariable Long id) {

        // busca uma notificação pelo identificador
        NotificacaoResponseDTO notificacao =
                notificacaoService.findById(id);

        return ResponseEntity.ok(notificacao);
    }

    //lista notificações de um cliente
    @Override
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<NotificacaoResponseDTO>> findByClienteId(
            @PathVariable Long clienteId) {

        // busca todas as notificações do cliente informado
        List<NotificacaoResponseDTO> notificacoes =
                notificacaoService.findByClienteId(clienteId);

        return ResponseEntity.ok(notificacoes);
    }

    //lista notificações não lidas de um cliente
    @Override
    @GetMapping("/cliente/{clienteId}/nao-lidas")
    public ResponseEntity<List<NotificacaoResponseDTO>> findNaoLidasByClienteId(
            @PathVariable Long clienteId) {

        // busca apenas as notificações não lidas do cliente informado
        List<NotificacaoResponseDTO> notificacoes =
                notificacaoService.findNaoLidasByClienteId(clienteId);

        return ResponseEntity.ok(notificacoes);
    }

    @Override
    @PatchMapping("/{id}/lida")
    public ResponseEntity<NotificacaoResponseDTO> marcarComoLida(
            @PathVariable Long id) {

        // marca a notificação informada como lida
        NotificacaoResponseDTO notificacao =
                notificacaoService.marcarComoLida(id);

        return ResponseEntity.ok(notificacao);
    }

    @Override
    @PatchMapping("/cliente/{clienteId}/lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(
            @PathVariable Long clienteId) {

        // marca todas as notificações do cliente como lidas
        notificacaoService.marcarTodasComoLidas(clienteId);

        return ResponseEntity.noContent().build();
    }

    //atualiza uma notificação
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody NotificacaoRequestDTO notificacaoDTO) {

        // atualiza os dados da notificação
        NotificacaoResponseDTO notificacaoAtualizada =
                notificacaoService.update(id, notificacaoDTO);

        return ResponseEntity.ok(notificacaoAtualizada);
    }

    //remove uma notificação pelo id
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        // remove a notificação informada
        notificacaoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}