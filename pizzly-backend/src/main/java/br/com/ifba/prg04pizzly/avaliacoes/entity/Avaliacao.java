package br.com.ifba.prg04pizzly.avaliacoes.entity;

import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//representa uma avaliação feita por um cliente após um pedido
@Entity
@Data
public class Avaliacao extends PersistenceEntity {

    private Integer nota;

    private String comentario;

    private LocalDateTime dataAvaliacao;

    //muitas avaliações podem ser feitas por um mesmo cliente. Logo, relacionamento ManyToOne
    @ManyToOne //um pedido pode ter no máximo uma avaliação. Isso evita o cliente avaliar o mesmo pedido várias vezes
    @JoinColumn(name = "cliente_id", nullable = false) //A coluna cliente_id guarda o vínculo com o cliente avaliador.
    private Cliente cliente;

    //um pedido pode possuir no máximo uma avaliação, logo, OneToOne
    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false) //pedido_id guarda o vínculo com o pedido avaliado
    private Pedido pedido;
}