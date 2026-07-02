package br.com.ifba.prg04pizzly.enderecos.dto;

import br.com.ifba.prg04pizzly.enderecos.entity.enums.TipoEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// esse DTO é usado para receber os dados de cadastro e atualização de endereço.
@Data
public class EnderecoRequestDTO {

    @NotNull(message = "o tipo do endereço é obrigatório")
    private TipoEndereco tipo; //enum (ou seja, não é texto puro. Por isso o @NotNull)

    @NotBlank(message = "o CEP é obrigatório")
    private String cep;

    @NotBlank(message = "o logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "o número é obrigatório")
    private String numero;

    private String complemento;

    @NotBlank(message = "o bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "a cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "a UF é obrigatória")
    private String uf;

    private Boolean principal; //definir como endereço principal (opcional)

    @NotNull(message = "o cliente é obrigatório") //relação com cliente
    private Long clienteId;
}