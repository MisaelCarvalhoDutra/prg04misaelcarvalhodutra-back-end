package br.com.ifba.prg04pizzly.logs.dto;

import lombok.Data;

import java.time.LocalDateTime;

//DTO utilizado para devolver os dados dos logs de auditoria ao frontend
@Data
public class LogAuditoriaResponseDTO {

    private Long id;
    private String acao;
    private String entidade;
    private Long entidadeId;
    private String descricao;
    private LocalDateTime dataHora;

    //evitam devolver a entidade Funcionario inteira
    private Long funcionarioId;
    private String funcionarioNome;
    private String funcionarioPerfil;
}