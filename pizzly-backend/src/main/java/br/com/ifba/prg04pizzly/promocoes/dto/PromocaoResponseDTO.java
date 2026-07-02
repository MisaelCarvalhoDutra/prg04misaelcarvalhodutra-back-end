package br.com.ifba.prg04pizzly.promocoes.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//DTO utilizado para retorno para o front dos dados da promoção
@Data
public class PromocaoResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private BigDecimal precoPromocional;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean ativa;
    private String tag;
    private String imagem;
    private BigDecimal precoAntigo;

    //evita devolver a entidade inteira, só devolve o necessário para o front, no caso os ids e os nomes dos produts
    private List<Long> produtosIds;
    private List<String> produtosNomes;
}