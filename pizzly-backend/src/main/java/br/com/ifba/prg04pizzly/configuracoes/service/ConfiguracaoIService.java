package br.com.ifba.prg04pizzly.configuracoes.service;

import br.com.ifba.prg04pizzly.configuracoes.dto.ConfiguracaoRequestDTO;
import br.com.ifba.prg04pizzly.configuracoes.dto.ConfiguracaoResponseDTO;

//Interface responsável pelas regras de negócio de configuração
public interface ConfiguracaoIService {


    //retorna as configurações atuais da pizzaria.
    ConfiguracaoResponseDTO buscar();

    //Atualiza as configurações da pizzaria e registra o funcionário responsável.
    ConfiguracaoResponseDTO atualizar(
            ConfiguracaoRequestDTO dto,
            Long funcionarioId);
}