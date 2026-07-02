package br.com.ifba.prg04pizzly.categorias.controller;

import br.com.ifba.prg04pizzly.categorias.dto.CategoriaRequestDTO;
import br.com.ifba.prg04pizzly.categorias.dto.CategoriaResponseDTO;
import br.com.ifba.prg04pizzly.categorias.service.CategoriaIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller responsável pelos endpoints de categoria, recebe as requisições HTTP e
// delega as regras de negócio ao CategoriaService.
@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController implements CategoriaIController {

    // service responsável pelas regras de negócio de categoria
    private final CategoriaIService categoriaService;

    @Override
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> save(
            @Valid @RequestBody CategoriaRequestDTO categoriaDTO,
            @RequestParam Long funcionarioId) {

        // cadastra a categoria informada
        CategoriaResponseDTO novaCategoria =
                categoriaService.save(categoriaDTO, funcionarioId);

        return ResponseEntity.status(201).body(novaCategoria);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> findAll(
            Pageable pageable) {

        // busca todas as categorias cadastradas
        Page<CategoriaResponseDTO> categorias =
                categoriaService.findAll(pageable);

        return ResponseEntity.ok(categorias);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(
            @PathVariable Long id) {

        // busca uma categoria pelo identificador
        CategoriaResponseDTO categoria =
                categoriaService.findById(id);

        return ResponseEntity.ok(categoria);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO categoriaDTO,
            @RequestParam Long funcionarioId) {

        // atualiza os dados da categoria
        CategoriaResponseDTO categoriaAtualizada =
                categoriaService.update(id, categoriaDTO, funcionarioId);

        return ResponseEntity.ok(categoriaAtualizada);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam Long funcionarioId) {

        // remove a categoria informada
        categoriaService.delete(id, funcionarioId);

        return ResponseEntity.noContent().build();
    }
}