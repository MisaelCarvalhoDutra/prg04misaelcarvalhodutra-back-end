package br.com.ifba.prg04pizzly.produtos.entity;

import br.com.ifba.prg04pizzly.categorias.entity.Categoria;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

//Representa um produto comercializado pela pizzaria
@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    // caminho ou URL da imagem exibida no cardápio
    private String imagem;

    private Boolean disponivel;

    //Muitos produtos podem pertencer a uma única categoria (relacionamento)
    //A coluna categoria_id guarda o vínculo do produto com sua categoria, cria essa coluna la na tabela produtos
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}