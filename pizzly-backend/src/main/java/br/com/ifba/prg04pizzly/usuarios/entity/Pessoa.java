package br.com.ifba.prg04pizzly.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

//Classe base com os dados comuns de qualquer pessoa do sistema.
//Não gera tabela própria no banco.
@MappedSuperclass /*classe q serve apenas para compartilhar atributos (abstrata)*/
@Data
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefone;
}