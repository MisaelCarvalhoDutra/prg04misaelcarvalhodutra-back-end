package br.com.ifba.prg04pizzly.categorias.dto;

import lombok.Data;

//esse DTO é utilizado para devolver os dados das categorias ao frontend
@Data
public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String icon;
}