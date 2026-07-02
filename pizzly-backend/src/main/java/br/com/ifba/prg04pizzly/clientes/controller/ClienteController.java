package br.com.ifba.prg04pizzly.clientes.controller;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteRequestDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteUpdateDTO;
import br.com.ifba.prg04pizzly.clientes.service.ClienteIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//controller responsável pelos endpoints de Cliente.
@RestController //transforma a classe em controller REST
@RequestMapping("/clientes") //define a rota base
@RequiredArgsConstructor
public class ClienteController implements ClienteIController {

    // injeta a camada de serviço
    private final ClienteIService clienteService;

    // cadastra um novo cliente
    @Override
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> save(
            @Valid @RequestBody ClienteRequestDTO clienteDTO) {

        ClienteResponseDTO novoCliente = clienteService.save(clienteDTO);

        return ResponseEntity.status(201).body(novoCliente);
    }

    // lista clientes utilizando paginação
    @Override
    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> findAll(Pageable pageable) {

        Page<ClienteResponseDTO> clientes = clienteService.findAll(pageable);

        return ResponseEntity.ok(clientes);
    }

    // busca cliente pelo id
    @Override
    @GetMapping("/{id}") //pega pelo id
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {

        ClienteResponseDTO cliente = clienteService.findById(id);

        return ResponseEntity.ok(cliente);
    }

    // atualiza cliente existente
    @Override
    @PutMapping("/{id}") //operação atualizar pelo id
    public ResponseEntity<ClienteResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteUpdateDTO clienteDTO) {

        ClienteResponseDTO clienteAtualizado =
                clienteService.update(id, clienteDTO);

        return ResponseEntity.ok(clienteAtualizado);
    }

    // remove cliente do sistema
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        clienteService.delete(id);

        return ResponseEntity.noContent().build();
    }
}