package br.com.ifba.prg04pizzly.itenspedido.dto;

import lombok.Data;

import java.math.BigDecimal;

//o DTO utilizado para retorno para o front dos dados do item do pedido
@Data
public class ItemPedidoResponseDTO {

    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;

    //não devolve o objeto Pedido nem o objeto Produto inteiro, devolve só o necessário para o frontend
    private Long pedidoId;
    private Long produtoId;
    private String produtoNome;
}