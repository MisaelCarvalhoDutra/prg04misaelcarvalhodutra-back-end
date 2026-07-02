package br.com.ifba.prg04pizzly.configuracoes.dto;

import lombok.Data;

import java.math.BigDecimal;

//DTO utilizado para retornar ao frontend as configurações atuais da pizzaria
@Data
public class ConfiguracaoResponseDTO {

    private Long id;

    private String nomePizzaria;

    private String whatsapp;

    private String endereco;

    private BigDecimal taxaEntrega;

    private String tempoEntrega;

    private Boolean aberta;
}