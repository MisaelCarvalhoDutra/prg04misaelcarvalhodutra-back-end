package br.com.ifba.prg04pizzly.entregas.controller;

import br.com.ifba.prg04pizzly.entregas.dto.EntregaRequestDTO;
import br.com.ifba.prg04pizzly.entregas.dto.EntregaResponseDTO;
import br.com.ifba.prg04pizzly.entregas.service.EntregaIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller responsável pelos endpoints de entrega.
// recebe as requisições HTTP e delega as regras de negócio ao EntregaService
@RestController
@RequestMapping("/entregas")
@RequiredArgsConstructor
public class EntregaController implements EntregaIController {

    // service responsável pelas regras de negócio de entrega
    private final EntregaIService entregaService;

    //Cadastra uma nova entrega
    @Override
    @PostMapping
    public ResponseEntity<EntregaResponseDTO> save(
            @Valid @RequestBody EntregaRequestDTO entregaDTO) {

        // cadastra a entrega informada
        EntregaResponseDTO novaEntrega =
                entregaService.save(entregaDTO);

        return ResponseEntity.status(201).body(novaEntrega);
    }

    //lista todas as entregas
    @Override
    @GetMapping
    public ResponseEntity<Page<EntregaResponseDTO>> findAll(
            Pageable pageable) {

        // busca todas as entregas cadastradas
        Page<EntregaResponseDTO> entregas =
                entregaService.findAll(pageable);

        return ResponseEntity.ok(entregas);
    }

    //busca uma entrega pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntregaResponseDTO> findById(
            @PathVariable Long id) {

        // busca uma entrega pelo identificador
        EntregaResponseDTO entrega =
                entregaService.findById(id);

        return ResponseEntity.ok(entrega);
    }

    //busca a entrega associada a um pedido
    @Override
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<EntregaResponseDTO> findByPedidoId(
            @PathVariable Long pedidoId) {

        // busca a entrega vinculada ao pedido informado
        EntregaResponseDTO entrega =
                entregaService.findByPedidoId(pedidoId);

        return ResponseEntity.ok(entrega);
    }

    //atualiza uma entrega existente
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EntregaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EntregaRequestDTO entregaDTO) {

        // atualiza os dados da entrega
        EntregaResponseDTO entregaAtualizada =
                entregaService.update(id, entregaDTO);

        return ResponseEntity.ok(entregaAtualizada);
    }

    //remove uma entrega
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        // remove a entrega informada
        entregaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}