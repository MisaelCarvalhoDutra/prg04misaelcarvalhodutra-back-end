package br.com.ifba.prg04pizzly.avaliacoes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//DTO utilizado para cadastro e atualização de avaliações
@Data
public class AvaliacaoRequestDTO {

    @NotNull(message = "a nota é obrigatória")
    @Min(value = 1, message = "a nota mínima é 1")
    @Max(value = 5, message = "a nota máxima é 5") //lá no front vai ser as "5 estrelas" pra marcar
    private Integer nota;

    private String comentario;

    @NotNull(message = "o cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "o pedido é obrigatório")
    private Long pedidoId;
}