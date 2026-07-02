package br.com.ifba.prg04pizzly.categorias.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//DTO utilizado para cadastro e atualização de categorias.
@Data
public class CategoriaRequestDTO {

    @NotBlank(message = "o nome é obrigatório")
    private String nome;

    private String descricao;

    private String icon;
}