package br.com.ifba.prg04pizzly.produtos.controller;

import br.com.ifba.prg04pizzly.produtos.dto.ProdutoRequestDTO;
import br.com.ifba.prg04pizzly.produtos.dto.ProdutoResponseDTO;
import br.com.ifba.prg04pizzly.produtos.service.ProdutoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//aqui o Controller responsável pelos endpoints de produto.
// ele recebe as requisições HTTP e delega as regras de negócio ao ProdutoService
@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController implements ProdutoIController {

    // service responsável pelas regras de negócio de produto
    private final ProdutoIService produtoService;

    @Override
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> save(
            @Valid @RequestBody ProdutoRequestDTO produtoDTO,
            @RequestParam Long funcionarioId) {

        // cadastra o produto informado
        ProdutoResponseDTO novoProduto =
                produtoService.save(produtoDTO, funcionarioId);

        return ResponseEntity.status(201).body(novoProduto);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> findAll(
            Pageable pageable) {

        // busca todos os produtos cadastrados
        Page<ProdutoResponseDTO> produtos =
                produtoService.findAll(pageable);

        return ResponseEntity.ok(produtos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(
            @PathVariable Long id) {

        // busca um produto pelo identificador
        ProdutoResponseDTO produto =
                produtoService.findById(id);

        return ResponseEntity.ok(produto);
    }

    @Override
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResponseDTO>> findByCategoriaId(
            @PathVariable Long categoriaId) {

        // busca todos os produtos pertencentes à categoria informada
        List<ProdutoResponseDTO> produtos =
                produtoService.findByCategoriaId(categoriaId);

        return ResponseEntity.ok(produtos);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO produtoDTO,
            @RequestParam Long funcionarioId) {

        // atualiza os dados do produto
        ProdutoResponseDTO produtoAtualizado =
                produtoService.update(id, produtoDTO, funcionarioId);

        return ResponseEntity.ok(produtoAtualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam Long funcionarioId) {

        // remove o produto informado
        produtoService.delete(id, funcionarioId);

        return ResponseEntity.noContent().build();
    }
}