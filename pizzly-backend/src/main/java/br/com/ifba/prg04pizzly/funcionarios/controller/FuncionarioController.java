package br.com.ifba.prg04pizzly.funcionarios.controller;

import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioRequestDTO;
import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioResponseDTO;
import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioUpdateDTO;
import br.com.ifba.prg04pizzly.funcionarios.service.FuncionarioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller responsável pelos endpoints de Funcionário
@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController implements FuncionarioIController {

    // injeta a camada de serviço
    private final FuncionarioIService funcionarioService;

    // cadastra um novo funcionário e registra o responsável pela ação
    @Override
    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> save(
            @Valid @RequestBody FuncionarioRequestDTO funcionarioDTO,
            @RequestParam Long funcionarioId) {

        FuncionarioResponseDTO novoFuncionario =
                funcionarioService.save(funcionarioDTO, funcionarioId);

        return ResponseEntity.status(201).body(novoFuncionario);
    }

    // lista funcionários utilizando paginação
    @Override
    @GetMapping
    public ResponseEntity<Page<FuncionarioResponseDTO>> findAll(
            Pageable pageable) {

        Page<FuncionarioResponseDTO> funcionarios =
                funcionarioService.findAll(pageable);

        return ResponseEntity.ok(funcionarios);
    }

    // busca funcionário pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> findById(
            @PathVariable Long id) {

        FuncionarioResponseDTO funcionario =
                funcionarioService.findById(id);

        return ResponseEntity.ok(funcionario);
    }

    // atualiza os dados básicos de um funcionário e registra o responsável pela ação
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioUpdateDTO funcionarioDTO,
            @RequestParam Long funcionarioId) {

        FuncionarioResponseDTO funcionarioAtualizado =
                funcionarioService.update(id, funcionarioDTO, funcionarioId);

        return ResponseEntity.ok(funcionarioAtualizado);
    }

    // remove funcionário do sistema e registra o responsável pela ação
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam Long funcionarioId) {

        funcionarioService.delete(id, funcionarioId);

        return ResponseEntity.noContent().build();
    }

    // ativa ou inativa funcionário e registra o responsável pela ação
    @Override
    @PatchMapping("/{id}/status")
    public ResponseEntity<FuncionarioResponseDTO> alterarStatus(
            @PathVariable Long id,
            @RequestParam Boolean ativo,
            @RequestParam Long funcionarioId) {

        return ResponseEntity.ok(
                funcionarioService.alterarStatus(id, ativo, funcionarioId));
    }
}