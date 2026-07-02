package br.com.ifba.prg04pizzly.configuracoes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

//DTO utilizado para receber os dados enviados pelo frontend para atualização das configurações
@Data
public class ConfiguracaoRequestDTO {

    @NotBlank(message = "O nome da pizzaria é obrigatório.")
    private String nomePizzaria;

    @NotBlank(message = "O WhatsApp é obrigatório.")
    private String whatsapp;

    private String endereco;

    @NotNull(message = "A taxa de entrega é obrigatória.")
    private BigDecimal taxaEntrega;

    private String tempoEntrega;

    @NotNull(message = "É necessário informar se a pizzaria está aberta.")
    private Boolean aberta;
}