package br.com.ifba.prg04pizzly.enderecos.entity;

import br.com.ifba.prg04pizzly.enderecos.entity.enums.TipoEndereco;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import jakarta.persistence.*;
import lombok.Data;

//representa um endereço de entrega cadastrado por um cliente.
@Entity
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoEndereco tipo; //Casa, Trabalho ou Outro

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    private Boolean principal;

    //significa que muitos endereços podem pertencer a um único cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) //cria a coluna "cliente_id" na tabela enderecos
    private Cliente cliente;
}