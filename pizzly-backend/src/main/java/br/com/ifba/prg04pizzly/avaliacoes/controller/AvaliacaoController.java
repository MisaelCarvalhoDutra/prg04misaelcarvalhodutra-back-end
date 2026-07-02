package br.com.ifba.prg04pizzly.avaliacoes.controller;

import br.com.ifba.prg04pizzly.avaliacoes.dto.AvaliacaoRequestDTO;
import br.com.ifba.prg04pizzly.avaliacoes.dto.AvaliacaoResponseDTO;
import br.com.ifba.prg04pizzly.avaliacoes.service.AvaliacaoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller responsável pelos endpoints de avaliação, recebe as requisições HTTP

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController implements AvaliacaoIController {

    private final AvaliacaoIService avaliacaoService;

    //cadastra uma nova avaliação
    @Override
    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> save(
            @Valid @RequestBody AvaliacaoRequestDTO avaliacaoDTO) {

        // cadastra a avaliação informada
        AvaliacaoResponseDTO novaAvaliacao =
                avaliacaoService.save(avaliacaoDTO);

        return ResponseEntity.status(201).body(novaAvaliacao);
    }

    //lista todas as avaliações
    @Override
    @GetMapping
    public ResponseEntity<Page<AvaliacaoResponseDTO>> findAll(
            Pageable pageable) {

        // busca todas as avaliações cadastradas
        Page<AvaliacaoResponseDTO> avaliacoes =
                avaliacaoService.findAll(pageable);

        return ResponseEntity.ok(avaliacoes);
    }

    //busca uma avaliação pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> findById(
            @PathVariable Long id) {

        // busca uma avaliação pelo identificador
        AvaliacaoResponseDTO avaliacao =
                avaliacaoService.findById(id);

        return ResponseEntity.ok(avaliacao);
    }

    //lista as avaliações feitas por um cliente
    @Override
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> findByClienteId(
            @PathVariable Long clienteId) {

        // busca todas as avaliações feitas pelo cliente informado
        List<AvaliacaoResponseDTO> avaliacoes =
                avaliacaoService.findByClienteId(clienteId);

        return ResponseEntity.ok(avaliacoes);
    }

    //busca a avaliação vinculada a um pedido
    @Override
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<AvaliacaoResponseDTO> findByPedidoId(
            @PathVariable Long pedidoId) {

        // busca a avaliação vinculada ao pedido informado
        AvaliacaoResponseDTO avaliacao =
                avaliacaoService.findByPedidoId(pedidoId);

        return ResponseEntity.ok(avaliacao);
    }

    //atualiza uma avaliação existente
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoRequestDTO avaliacaoDTO) {

        // atualiza os dados da avaliação
        AvaliacaoResponseDTO avaliacaoAtualizada =
                avaliacaoService.update(id, avaliacaoDTO);

        return ResponseEntity.ok(avaliacaoAtualizada);
    }

    //remove uma avaliação pelo id
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        // remove a avaliação informada
        avaliacaoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}