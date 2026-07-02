package br.com.ifba.prg04pizzly.configuracoes.controller;

import br.com.ifba.prg04pizzly.configuracoes.dto.ConfiguracaoRequestDTO;
import br.com.ifba.prg04pizzly.configuracoes.dto.ConfiguracaoResponseDTO;
import org.springframework.http.ResponseEntity;

//Interface dos endpoints de configuração
public interface ConfiguracaoIController {

    // buscar configurações atuais
    ResponseEntity<ConfiguracaoResponseDTO> buscar();

    // atualizar configurações informando o funcionário responsável
    ResponseEntity<ConfiguracaoResponseDTO> atualizar(
            ConfiguracaoRequestDTO dto,
            Long funcionarioId);
}