package br.com.ifba.prg04pizzly.pedidos.dto;

import br.com.ifba.prg04pizzly.pedidos.entity.enums.StatusPedido;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//DTO utilizado para devolver os dados do pedido ao frontend
@Data
public class PedidoResponseDTO {

    private Long id;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal subtotal;
    private BigDecimal taxaEntrega;
    private BigDecimal total;
    private String observacao;
    private String formaRecebimento;
    private Long enderecoId;

    //não devolve o objeto Cliente inteiro, devolve só o necessário para o frontend
    private Long clienteId;
    private String clienteNome;
}