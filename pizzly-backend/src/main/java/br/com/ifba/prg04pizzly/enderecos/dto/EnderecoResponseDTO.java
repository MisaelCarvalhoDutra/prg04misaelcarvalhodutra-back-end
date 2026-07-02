package br.com.ifba.prg04pizzly.enderecos.dto;

import br.com.ifba.prg04pizzly.enderecos.entity.enums.TipoEndereco;
import lombok.Data;

//DTO usado para devolver ao front os dados do endereço cadastrado
@Data
public class EnderecoResponseDTO {

    private Long id;
    private TipoEndereco tipo;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private Boolean principal;

    //evita devolver o objeto Cliente inteiro, retorna só o que a tela realmente precisa
    private Long clienteId;
    private String clienteNome;
}