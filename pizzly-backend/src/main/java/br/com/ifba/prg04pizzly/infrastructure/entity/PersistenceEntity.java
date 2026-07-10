package br.com.ifba.prg04pizzly.infrastructure.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

//classe base para reaproveitar o campo id nas entidades do sistema
@Getter
@Setter
@MappedSuperclass //possui @MappedSuperclass, portanto não será criada uma tabela, apenas para reaproveitamento
public abstract class PersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}