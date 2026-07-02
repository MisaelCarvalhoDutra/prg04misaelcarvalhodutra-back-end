package br.com.ifba.prg04pizzly.notificacoes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//esse DTO é utilizado para cadastro e atualização de notificações
@Data
public class NotificacaoRequestDTO {

    @NotBlank(message = "o título é obrigatório")
    private String titulo;

    // conteúdo exibido ao cliente
    @NotBlank(message = "a mensagem é obrigatória")
    private String mensagem;

    // id do cliente que receberá a notificação
    @NotNull(message = "o cliente é obrigatório")
    private Long clienteId;
}