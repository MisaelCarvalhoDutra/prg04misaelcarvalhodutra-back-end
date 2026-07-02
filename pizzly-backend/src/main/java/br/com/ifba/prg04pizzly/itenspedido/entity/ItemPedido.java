package br.com.ifba.prg04pizzly.itenspedido.entity;

import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import br.com.ifba.prg04pizzly.produtos.entity.Produto;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

//representa um item pertencente a um pedido
@Entity
@Data
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;

    //relacionamentos:

    //muitos itens podem pertencer a um único pedido. logo, ManyToOne
    //ou seja, um pedido pode ter vários itens, mas cada item pertence a um único pedido.
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    //muitos itens podem estar relacionados a um único produto. logo, ManyToOne
    //ou seja,um produto pode aparecer em vários itens de pedidos diferentes, mas cada item aponta para um produto
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
}