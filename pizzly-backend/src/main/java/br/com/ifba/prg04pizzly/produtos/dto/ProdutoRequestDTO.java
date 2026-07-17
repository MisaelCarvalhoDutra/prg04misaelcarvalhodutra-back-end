package br.com.ifba.prg04pizzly.produtos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

//DTO utilizado para cadastro e atualização de produtos
@Data
public class ProdutoRequestDTO {

    @NotBlank(message = "o nome é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "o preço é obrigatório")
    private BigDecimal preco;

    @Size(max = 1000, message = "a URL da imagem deve possuir no máximo 1000 caracteres")
    private String imagem;

    private Boolean disponivel;

    @NotNull(message = "a categoria é obrigatória")
    private Long categoriaId; //é usado para informar qual categoria será vinculada ao produto.
}