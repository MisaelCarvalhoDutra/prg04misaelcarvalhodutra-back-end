package br.com.ifba.prg04pizzly.usuarios.entity;

import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

//Classe base com os dados comuns de qualquer pessoa do sistema.
//Não gera tabela própria no banco.
@MappedSuperclass /*classe q serve apenas para compartilhar atributos (abstrata)*/
@Data
@EqualsAndHashCode(callSuper = true) //inclui os atributos herdados da superclasse (PersistenceEntity) na comparação
public abstract class Pessoa extends PersistenceEntity {

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefone;
}