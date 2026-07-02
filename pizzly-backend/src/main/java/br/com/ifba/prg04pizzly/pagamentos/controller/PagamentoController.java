package br.com.ifba.prg04pizzly.pagamentos.controller;

import br.com.ifba.prg04pizzly.pagamentos.dto.PagamentoRequestDTO;
import br.com.ifba.prg04pizzly.pagamentos.dto.PagamentoResponseDTO;
import br.com.ifba.prg04pizzly.pagamentos.entity.enums.StatusPagamento;
import br.com.ifba.prg04pizzly.pagamentos.service.PagamentoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//aqui o Controller responsável pelos endpoints de pagamento,
// recebe as requisições HTTP e delega as regras de negócio ao PagamentoService
@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController implements PagamentoIController {

    // service responsável pelas regras de negócio de pagamento
    private final PagamentoIService pagamentoService;

    //cadastra um novo pagamento
    @Override
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> save(
            @Valid @RequestBody PagamentoRequestDTO pagamentoDTO) {

        // cadastra o pagamento informado
        PagamentoResponseDTO novoPagamento =
                pagamentoService.save(pagamentoDTO);

        return ResponseEntity.status(201).body(novoPagamento);
    }

    //lista todos os pagamentos
    @Override
    @GetMapping
    public ResponseEntity<Page<PagamentoResponseDTO>> findAll(
            Pageable pageable) {

        // busca todos os pagamentos cadastrados
        Page<PagamentoResponseDTO> pagamentos =
                pagamentoService.findAll(pageable);

        return ResponseEntity.ok(pagamentos);
    }

    //busca um pagamento pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> findById(
            @PathVariable Long id) {

        // busca um pagamento pelo identificador
        PagamentoResponseDTO pagamento =
                pagamentoService.findById(id);

        return ResponseEntity.ok(pagamento);
    }

    //busca o pagamento associado a um pedido
    @Override
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<PagamentoResponseDTO> findByPedidoId(
            @PathVariable Long pedidoId) {

        // busca o pagamento vinculado ao pedido informado
        PagamentoResponseDTO pagamento =
                pagamentoService.findByPedidoId(pedidoId);

        return ResponseEntity.ok(pagamento);
    }

    //atualiza um pagamento existente
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PagamentoRequestDTO pagamentoDTO) {

        // atualiza os dados do pagamento
        PagamentoResponseDTO pagamentoAtualizado =
                pagamentoService.update(id, pagamentoDTO);

        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @Override
    @PatchMapping("/{id}/status")
    public ResponseEntity<PagamentoResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam StatusPagamento statusPagamento) {

        // atualiza apenas o status do pagamento
        PagamentoResponseDTO pagamentoAtualizado =
                pagamentoService.updateStatus(id, statusPagamento);

        return ResponseEntity.ok(pagamentoAtualizado);
    }

    //remove um pagamento pelo id
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        // remove o pagamento informado
        pagamentoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}