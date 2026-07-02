package br.com.ifba.prg04pizzly.notificacoes.entity;

import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//representa uma notificação recebida por um cliente
@Entity
@Data
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    // data e hora em que a notificação foi criada
    private LocalDateTime dataEnvio;

    // indica se o cliente já visualizou a notificação
    private Boolean lida;

    //um cliente pode receber várias notificações durante o uso do sistema.
    // Cada notificação pertence a apenas um cliente, então a cardinalidade é Muitos para Um (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}