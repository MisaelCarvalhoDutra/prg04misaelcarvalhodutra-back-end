package br.com.ifba.prg04pizzly.pedidos.entity;

import br.com.ifba.prg04pizzly.entregas.entity.enums.FormaRecebimento;
import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04pizzly.pedidos.entity.enums.StatusPedido;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//representa um pedido realizado por um cliente
@Entity
@Data
public class Pedido extends PersistenceEntity {

    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private BigDecimal subtotal;

    private BigDecimal taxaEntrega;

    private BigDecimal total;

    private String observacao;

    // define se o pedido será entregue ou retirado no balcão
    @Enumerated(EnumType.STRING)
    private FormaRecebimento formaRecebimento;

    // armazena o id do endereço escolhido quando a forma de recebimento for entrega
    private Long enderecoId;

    // Muitos pedidos podem pertencer a um único cliente, logo ManyToOne (relacionamento)
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)//a coluna cliente_id guarda o vínculo com o cliente que realizou o pedido
    private Cliente cliente;
}