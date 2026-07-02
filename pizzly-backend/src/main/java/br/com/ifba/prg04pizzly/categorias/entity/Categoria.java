package br.com.ifba.prg04pizzly.categorias.entity;

import jakarta.persistence.*;
import lombok.Data;

//Representa uma categoria utilizada para organizar os produtos do cardápio
@Entity
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    // ícone utilizado na interface para representar visualmente a categoria
    private String icon;
}