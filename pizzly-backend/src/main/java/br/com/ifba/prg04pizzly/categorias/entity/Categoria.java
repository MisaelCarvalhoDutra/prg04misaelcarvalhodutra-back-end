package br.com.ifba.prg04pizzly.categorias.entity;

import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Data;

//Representa uma categoria utilizada para organizar os produtos do cardápio
@Entity
@Data
public class Categoria extends PersistenceEntity {

    private String nome;

    private String descricao;

    // ícone utilizado na interface para representar visualmente a categoria (visual)
    private String icon;
}