package br.com.ifba.prg04pizzly.notificacoes.dto;

import lombok.Data;

import java.time.LocalDateTime;

//usa esse DTO para retorno para o front dos dados da notificação
@Data
public class NotificacaoResponseDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataEnvio;
    private Boolean lida;

    // identifica o cliente dono da notificação
    private Long clienteId;

    // nome do cliente para facilitar o retorno ao front
    private String clienteNome;
}