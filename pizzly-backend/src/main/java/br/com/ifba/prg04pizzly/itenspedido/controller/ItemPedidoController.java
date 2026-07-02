package br.com.ifba.prg04pizzly.itenspedido.controller;

import br.com.ifba.prg04pizzly.itenspedido.dto.ItemPedidoRequestDTO;
import br.com.ifba.prg04pizzly.itenspedido.dto.ItemPedidoResponseDTO;
import br.com.ifba.prg04pizzly.itenspedido.service.ItemPedidoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//esse é o Controller responsável pelos endpoints de item do pedido
@RestController
@RequestMapping("/itens-pedido")
@RequiredArgsConstructor
public class ItemPedidoController implements ItemPedidoIController {

    // service responsável pelas regras de negócio de item do pedido
    private final ItemPedidoIService itemPedidoService;

    // cadastra um novo item em um pedido
    @Override
    @PostMapping
    public ResponseEntity<ItemPedidoResponseDTO> save(
            @Valid @RequestBody ItemPedidoRequestDTO itemDTO) {

        // cadastra o item informado no pedido
        ItemPedidoResponseDTO novoItem =
                itemPedidoService.save(itemDTO);

        return ResponseEntity.status(201).body(novoItem);
    }

    // lista todos os itens de pedido
    @Override
    @GetMapping
    public ResponseEntity<Page<ItemPedidoResponseDTO>> findAll(
            Pageable pageable) {

        // busca todos os itens cadastrados
        Page<ItemPedidoResponseDTO> itens =
                itemPedidoService.findAll(pageable);

        return ResponseEntity.ok(itens);
    }

    // busca um item de pedido pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDTO> findById(
            @PathVariable Long id) {

        // busca um item pelo identificador
        ItemPedidoResponseDTO item =
                itemPedidoService.findById(id);

        return ResponseEntity.ok(item);
    }

    //lista os itens pertencentes a um pedido
    @Override
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItemPedidoResponseDTO>> findByPedidoId(
            @PathVariable Long pedidoId) {

        // busca todos os itens pertencentes ao pedido informado
        List<ItemPedidoResponseDTO> itens =
                itemPedidoService.findByPedidoId(pedidoId);

        return ResponseEntity.ok(itens);
    }

    //atualiza um item de pedido existente
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ItemPedidoRequestDTO itemDTO) {

        // atualiza os dados do item do pedido
        ItemPedidoResponseDTO itemAtualizado =
                itemPedidoService.update(id, itemDTO);

        return ResponseEntity.ok(itemAtualizado);
    }

    //remove um item de pedido
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        // remove o item informado
        itemPedidoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}