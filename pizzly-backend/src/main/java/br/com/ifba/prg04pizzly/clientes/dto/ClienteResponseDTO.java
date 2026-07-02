package br.com.ifba.prg04pizzly.clientes.dto;

import lombok.Data;

import java.time.LocalDateTime;

//esse é o DTO usado para devolver os dados do cliente sem expor a senha.
@Data
public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDateTime dataCadastro;
    private Boolean ativo;
}