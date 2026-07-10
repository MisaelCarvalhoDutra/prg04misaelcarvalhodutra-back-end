package br.com.ifba.prg04pizzly.promocoes.entity;

import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04pizzly.produtos.entity.Produto;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//Representa uma promoção da pizzaria
@Entity
@Data
public class Promocao extends PersistenceEntity {

    private String titulo;

    private String descricao;

    private BigDecimal precoPromocional;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Boolean ativa;

    private String tag;

    private String imagem;

    private BigDecimal precoAntigo;

    //Uma promoção pode incluir vários produtos,
    //e um produto também pode participar de várias promoções, logo ManyToMany
    @ManyToMany
    @JoinTable(
            name = "promocao_produto", //como é uma relaçção ManyToMany, vai gerar uma terceira tabela, "promocao_produto",serve apenas para armazenar os relacionamentos.
            joinColumns = @JoinColumn(name = "promocao_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos;
}