package br.com.ifba.prg04pizzly.itenspedido.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

//aqui o DTO utilizado para cadastro e atualização de itens de pedido
@Data
public class ItemPedidoRequestDTO {

    @NotNull(message = "a quantidade é obrigatória")
    @Positive(message = "a quantidade deve ser maior que zero") //o @Positive é importante porque impede quantidade 0 ou negativa
    private Integer quantidade;

    @NotNull(message = "o pedido é obrigatório")
    private Long pedidoId;

    @NotNull(message = "o produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "o preço unitário é obrigatório")
    private BigDecimal precoUnitario;
}