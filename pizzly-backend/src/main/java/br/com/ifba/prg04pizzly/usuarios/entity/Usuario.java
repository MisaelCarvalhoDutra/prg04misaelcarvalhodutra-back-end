package br.com.ifba.prg04pizzly.usuarios.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

//Classe base para os usuários do sistema.
//tem os dados de autenticação e controle.
@Entity
//cada classe filha possui sua propria tabela
@Inheritance(strategy = InheritanceType.JOINED)  //id compartilhado (herança relacional)
@Data
@EqualsAndHashCode(callSuper = true) //inclui os atributos herdados da super classe na comparação (Pessoa)
public abstract class Usuario extends Pessoa {

    private String senha;

    private LocalDateTime dataCadastro;

    private Boolean ativo; //pra bloquear login
}