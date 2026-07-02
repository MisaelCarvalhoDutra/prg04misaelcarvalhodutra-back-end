package br.com.ifba.prg04pizzly.pedidos.controller;

import br.com.ifba.prg04pizzly.pedidos.dto.PedidoRequestDTO;
import br.com.ifba.prg04pizzly.pedidos.dto.PedidoResponseDTO;
import br.com.ifba.prg04pizzly.pedidos.entity.enums.StatusPedido;
import br.com.ifba.prg04pizzly.pedidos.service.PedidoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//controller responsável pelos endpoints de pedido.
//recebe as requisições HTTP e delega as regras de negócio ao PedidoService
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController implements PedidoIController {

    // service responsável pelas regras de negócio de pedido
    private final PedidoIService pedidoService;

    //cadastra um novo pedido
    @Override
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> save(
            @Valid @RequestBody PedidoRequestDTO pedidoDTO) {

        // cadastra o pedido informado
        PedidoResponseDTO novoPedido =
                pedidoService.save(pedidoDTO);

        return ResponseEntity.status(201).body(novoPedido);
    }

    //lista todos os pedidos
    @Override
    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> findAll(
            Pageable pageable) {

        // busca todos os pedidos cadastrados
        Page<PedidoResponseDTO> pedidos =
                pedidoService.findAll(pageable);

        return ResponseEntity.ok(pedidos);
    }

    //busca um pedido pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(
            @PathVariable Long id) {

        // busca um pedido pelo identificador
        PedidoResponseDTO pedido =
                pedidoService.findById(id);

        return ResponseEntity.ok(pedido);
    }

    //lista os pedidos de um cliente
    @Override
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> findByClienteId(
            @PathVariable Long clienteId) {

        // busca todos os pedidos realizados pelo cliente informado
        List<PedidoResponseDTO> pedidos =
                pedidoService.findByClienteId(clienteId);

        return ResponseEntity.ok(pedidos);
    }

    //atualiza um pedido existente
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PedidoRequestDTO pedidoDTO) {

        // atualiza os dados do pedido
        PedidoResponseDTO pedidoAtualizado =
                pedidoService.update(id, pedidoDTO);

        return ResponseEntity.ok(pedidoAtualizado);
    }

    //atualiza apenas o status do pedido
    @Override
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido status,
            @RequestParam Long funcionarioId) {

        // atualiza apenas o status do pedido
        PedidoResponseDTO pedidoAtualizado =
                pedidoService.updateStatus(id, status, funcionarioId);

        return ResponseEntity.ok(pedidoAtualizado);
    }

    //remove um pedido
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        // remove o pedido informado
        pedidoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}