package br.com.ifba.prg04pizzly.configuracoes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


//Representa as configurações gerais da pizzaria. Armazena informações exibidas no sistema e utilizadas
//durante o funcionamento da aplicação.
//obs: alteradas apenas pelo administrador na aba admin la no front
@Entity
@Data
public class Configuracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomePizzaria;

    private String whatsapp;

    private String endereco;

    // valor fixo cobrado pela entrega
    private BigDecimal taxaEntrega;

    // tempo médio estimado para entrega
    private String tempoEntrega;

    // indica se a pizzaria está aberta para receber pedidos
    private Boolean aberta;
}