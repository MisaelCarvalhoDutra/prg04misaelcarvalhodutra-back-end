package br.com.ifba.prg04pizzly.produtos.dto;

import lombok.Data;

import java.math.BigDecimal;

//DTO utilizado para devolver os dados do produto ao frontend
@Data
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String imagem;
    private Boolean disponivel;

    //não devolve a entidade Categoria inteira, só o ID e o nome que o frontend precisa mostrar
    private Long categoriaId;
    private String categoriaNome;
}