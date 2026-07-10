package br.com.ifba.prg04pizzly.pagamentos.entity;

import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04pizzly.pagamentos.entity.enums.FormaPagamento;
import br.com.ifba.prg04pizzly.pagamentos.entity.enums.StatusPagamento;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

//Representa o pagamento de um pedido
@Entity
@Data
public class Pagamento extends PersistenceEntity {

    @Enumerated(EnumType.STRING)
    // armazena a forma de pagamento como texto no banco
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    // armazena o status do pagamento como texto no banco
    private StatusPagamento statusPagamento;

    private BigDecimal valorTroco;

    //cada pedido possui exatamente um pagamento e cada pagamento pertence a exatamente um pedido. Logo, OneToOne
    //a coluna pedido_id faz a ligação entre as duas entidades
    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
}