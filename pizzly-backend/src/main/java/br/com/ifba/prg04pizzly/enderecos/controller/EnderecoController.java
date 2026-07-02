package br.com.ifba.prg04pizzly.enderecos.controller;

import br.com.ifba.prg04pizzly.enderecos.dto.EnderecoRequestDTO;
import br.com.ifba.prg04pizzly.enderecos.dto.EnderecoResponseDTO;
import br.com.ifba.prg04pizzly.enderecos.service.EnderecoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//aqui o Controller responsável pelos endpoints de endereço.
//ele recebe as requisições HTTP e delega as regras de negócio ao EnderecoService
@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController implements EnderecoIController {

    // service responsável pelas regras de negócio de endereço
    private final EnderecoIService enderecoService;

    // cadastra um novo endereço
    @Override
    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> save(
            @Valid @RequestBody EnderecoRequestDTO enderecoDTO) {

        // cadastra o endereço informado
        EnderecoResponseDTO novoEndereco =
                enderecoService.save(enderecoDTO);

        return ResponseEntity.status(201).body(novoEndereco);
    }

    // lista endereços utilizando paginação
    @Override
    @GetMapping
    public ResponseEntity<Page<EnderecoResponseDTO>> findAll(
            Pageable pageable) {

        // busca todos os endereços cadastrados
        Page<EnderecoResponseDTO> enderecos =
                enderecoService.findAll(pageable);

        return ResponseEntity.ok(enderecos);
    }

    // busca endereço pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> findById(
            @PathVariable Long id) {

        // busca um endereço pelo identificador
        EnderecoResponseDTO endereco =
                enderecoService.findById(id);

        return ResponseEntity.ok(endereco);
    }

    // lista os endereços de um cliente específico
    @Override
    @GetMapping("/cliente/{clienteId}") //esse endpoint permite listar todos os endereços pertencentes a um cliente específico.
    public ResponseEntity<List<EnderecoResponseDTO>> findByClienteId(
            @PathVariable Long clienteId) {

        // busca todos os endereços pertencentes ao cliente informado
        List<EnderecoResponseDTO> enderecos =
                enderecoService.findByClienteId(clienteId);

        return ResponseEntity.ok(enderecos);
    }

    // atualiza endereço existente
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EnderecoRequestDTO enderecoDTO) {

        // atualiza os dados do endereço
        EnderecoResponseDTO enderecoAtualizado =
                enderecoService.update(id, enderecoDTO);

        return ResponseEntity.ok(enderecoAtualizado);
    }

    // remove endereço do sistema
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        // remove o endereço informado
        enderecoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // define um endereço como principal
    @Override
    @PatchMapping("/{id}/principal") //esse endpoint altera somente esse atributo, sem atualizar os demais dados do
    // endereço. Endpoint específico (PATCH) para uma alteração parcial
    public ResponseEntity<EnderecoResponseDTO> definirPrincipal(
            @PathVariable Long id) {

        // define o endereço informado como principal
        EnderecoResponseDTO endereco =
                enderecoService.definirPrincipal(id);

        return ResponseEntity.ok(endereco);
    }
}