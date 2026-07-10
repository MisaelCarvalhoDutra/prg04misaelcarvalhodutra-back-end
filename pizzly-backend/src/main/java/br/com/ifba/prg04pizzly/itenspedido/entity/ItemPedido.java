package br.com.ifba.prg04pizzly.itenspedido.entity;

import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import br.com.ifba.prg04pizzly.produtos.entity.Produto;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

//Representa um item pertencente a um pedido.
@Entity
@Data
public class ItemPedido extends PersistenceEntity {

    private Integer quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;

    //Muitos itens podem pertencer a um único pedido, formando uma relação ManyToOne
    //Um pedido pode ter vários itens, mas cada item pertence a um único pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    //Muitos itens podem estar relacionados a um único produto, formando uma relação ManyToOne
    //Um produto pode aparecer em vários pedidos, mas cada item aponta para um único produto
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
}