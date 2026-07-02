package br.com.ifba.prg04pizzly.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//esse DTO é utilizado para receber os dados de login lá do front end.
@Data
public class LoginRequestDTO {

    @NotBlank(message = "o email é obrigatório") //impedindo campo vazio
    @Email(message = "email inválido") //valida o formato do email
    private String email;

    @NotBlank(message = "a senha é obrigatória")
    private String senha;
}