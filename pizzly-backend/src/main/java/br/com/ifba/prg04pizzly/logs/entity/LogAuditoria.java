package br.com.ifba.prg04pizzly.logs.entity;

import br.com.ifba.prg04pizzly.usuarios.entity.Funcionario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

//Registra ações administrativas realizadas por funcionários.
//Permite rastrear alterações importantes no sistema
@Entity
@Data
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acao;

    private String entidade;

    private Long entidadeId;

    private String descricao;

    private LocalDateTime dataHora;

    //Funcionário responsável pela ação registrada.
    //pode ser nulo caso a ação não tenha um funcionário associado
    @ManyToOne //vários logs podem pertencer a um único funcionário. Logo, relacionamento ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
}