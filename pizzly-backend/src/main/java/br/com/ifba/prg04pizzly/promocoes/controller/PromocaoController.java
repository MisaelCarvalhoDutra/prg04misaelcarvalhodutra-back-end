package br.com.ifba.prg04pizzly.promocoes.controller;

import br.com.ifba.prg04pizzly.promocoes.dto.PromocaoRequestDTO;
import br.com.ifba.prg04pizzly.promocoes.dto.PromocaoResponseDTO;
import br.com.ifba.prg04pizzly.promocoes.service.PromocaoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controller responsável pelos endpoints da entidade Promoção
@RestController
@RequestMapping("/promocoes")
@RequiredArgsConstructor
public class PromocaoController implements PromocaoIController {

    // service responsável pelas regras de negócio de promoção
    private final PromocaoIService promocaoService;

    // cadastra a promoção informada
    @Override
    @PostMapping
    public ResponseEntity<PromocaoResponseDTO> save(
            @Valid @RequestBody PromocaoRequestDTO promocaoDTO,
            @RequestParam Long funcionarioId) {

        PromocaoResponseDTO novaPromocao =
                promocaoService.save(promocaoDTO, funcionarioId);

        return ResponseEntity.status(201).body(novaPromocao);
    }

    //Lista todas as promoções
    @Override
    @GetMapping
    public ResponseEntity<Page<PromocaoResponseDTO>> findAll(
            Pageable pageable) {

        // busca todas as promoções cadastradas
        Page<PromocaoResponseDTO> promocoes =
                promocaoService.findAll(pageable);

        return ResponseEntity.ok(promocoes);
    }

    //busca uma promoção pelo id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PromocaoResponseDTO> findById(
            @PathVariable Long id) {

        // busca uma promoção pelo identificador
        PromocaoResponseDTO promocao =
                promocaoService.findById(id);

        return ResponseEntity.ok(promocao);
    }

    //atualiza uma promoção existente
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PromocaoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PromocaoRequestDTO promocaoDTO,
            @RequestParam Long funcionarioId) {

        // atualiza os dados da promoção
        PromocaoResponseDTO promocaoAtualizada =
                promocaoService.update(id, promocaoDTO, funcionarioId);

        return ResponseEntity.ok(promocaoAtualizada);
    }

    //remove uma promoção
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam Long funcionarioId) {

        // remove a promoção informada
        promocaoService.delete(id, funcionarioId);

        return ResponseEntity.noContent().build();
    }
}