package br.com.ifba.prg04pizzly.promocoes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//DTO utilizado para cadastro e atualização de promoções
@Data
public class PromocaoRequestDTO {

    @NotBlank(message = "o título é obrigatório")
    private String titulo;

    private String descricao;

    @NotNull(message = "o preço promocional é obrigatório")
    private BigDecimal precoPromocional;

    @NotNull(message = "a data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "a data de fim é obrigatória")
    private LocalDate dataFim;

    private String tag;

    private String imagem;

    private BigDecimal precoAntigo;

    private Boolean ativa;

    // é @NotEmpty pois a lista obrigatoriamente deve possuir pelo menos um elemento.
    @NotEmpty(message = "a promoção deve possuir pelo menos um produto")
    private List<Long> produtosIds;

}