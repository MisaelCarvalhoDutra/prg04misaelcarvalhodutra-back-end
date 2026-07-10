package br.com.ifba.prg04pizzly.entregas.entity;

import br.com.ifba.prg04pizzly.enderecos.entity.Endereco;
import br.com.ifba.prg04pizzly.entregas.entity.enums.FormaRecebimento;
import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import jakarta.persistence.*;
import lombok.Data;

//representa a entrega ou retirada de um pedido
@Entity
@Data
public class Entrega extends PersistenceEntity {

    //enum q define se o pedido será entregue ou retirado no local
    @Enumerated(EnumType.STRING)
    private FormaRecebimento formaRecebimento;

    private String horarioPreferido;

    private String tempoEstimado;

    private String observacao;

    //um pedido gera uma entrega ou retirada. Então cada entrega pertence a um único pedido. Logo, OneToOne
    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    //vários pedidos podem usar o mesmo endereço do cliente. Logo, ManyToOne
    //Mas se for retirada no balcão, esse campo pode ficar vazio
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}