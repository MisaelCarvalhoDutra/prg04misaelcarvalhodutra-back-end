package br.com.ifba.prg04pizzly.clientes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


//aqui é DTO utilizado para receber os dados necessários para o cadastro de um novo cliente.
@Data
public class ClienteRequestDTO {

    @NotBlank(message = "o nome é obrigatório")
    private String nome;

    @NotBlank(message = "o email é obrigatório")//garante que o campo não seja nulo, vazio ou composto apenas por espaços.
    @Email(message = "Email inválido") //valida formato invalido
    private String email;

    private String telefone;

    @NotBlank(message = "a senha é obrigatória")
    @Size(min = 8, message = "a senha deve ter no mínimo 8 caracteres")
    private String senha;

}