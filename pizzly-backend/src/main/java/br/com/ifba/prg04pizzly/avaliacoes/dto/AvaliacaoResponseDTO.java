package br.com.ifba.prg04pizzly.avaliacoes.dto;

import lombok.Data;

import java.time.LocalDateTime;

//DTO utilizado para devolver os dados da avaliação ao frontend
@Data
public class AvaliacaoResponseDTO {

    private Long id;
    private Integer nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;

    //evitam retornar os objetos completos de Cliente e Pedido, retornando só o necessário para o front
    private Long clienteId;
    private String clienteNome;

    private Long pedidoId;
}