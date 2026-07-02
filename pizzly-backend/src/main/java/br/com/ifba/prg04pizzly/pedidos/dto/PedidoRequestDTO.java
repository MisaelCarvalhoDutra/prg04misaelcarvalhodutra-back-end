package br.com.ifba.prg04pizzly.pedidos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

//esse DTO recebe os dados enviados pelo frontend para criar ou atualizar um pedido.
@Data
public class PedidoRequestDTO {

    @NotNull(message = "o subtotal é obrigatório")
    private BigDecimal subtotal;

    @NotNull(message = "a taxa de entrega é obrigatória")
    private BigDecimal taxaEntrega;

    private String observacao;

    // forma escolhida pelo cliente: entrega ou retirada
    private String formaRecebimento;

    // id do endereço escolhido, quando a forma de recebimento for entrega
    private Long enderecoId;

    @NotNull(message = "o cliente é obrigatório")
    private Long clienteId;
}