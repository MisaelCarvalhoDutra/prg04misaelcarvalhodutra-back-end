package br.com.ifba.prg04pizzly.configuracoes.controller;
import br.com.ifba.prg04pizzly.configuracoes.controller.ConfiguracaoIController;
import br.com.ifba.prg04pizzly.configuracoes.service.ConfiguracaoIService;

import br.com.ifba.prg04pizzly.configuracoes.dto.ConfiguracaoRequestDTO;
import br.com.ifba.prg04pizzly.configuracoes.dto.ConfiguracaoResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//controller responsável pelos endpoints de configuração da pizzaria.
//recebe as requisições HTTP e delega as regras de negócio ao ConfiguracaoService
@RestController
@RequestMapping("/configuracoes")
@RequiredArgsConstructor
public class ConfiguracaoController implements ConfiguracaoIController {

    // service responsável pelas regras de negócio das configurações da pizzaria
    private final ConfiguracaoIService configuracaoService;

    @Override
    @GetMapping
    //busca as configurações atuais da pizzaria
    public ResponseEntity<ConfiguracaoResponseDTO> buscar() {

        // busca as configurações cadastradas
        ConfiguracaoResponseDTO configuracao =
                configuracaoService.buscar();

        return ResponseEntity.ok(configuracao);
    }

    @Override
    @PutMapping
    //atualiza as configurações da pizzaria
    public ResponseEntity<ConfiguracaoResponseDTO> atualizar(
            @RequestBody ConfiguracaoRequestDTO dto,
            @RequestParam Long funcionarioId) {

        // atualiza as configurações da pizzaria
        ConfiguracaoResponseDTO configuracaoAtualizada =
                configuracaoService.atualizar(dto, funcionarioId);

        return ResponseEntity.ok(configuracaoAtualizada);
    }
}