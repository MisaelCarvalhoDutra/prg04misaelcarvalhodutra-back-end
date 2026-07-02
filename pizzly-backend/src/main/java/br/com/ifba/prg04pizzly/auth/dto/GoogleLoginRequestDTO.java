package br.com.ifba.prg04pizzly.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//DTO usado para receber o token enviado pelo Google no login.
@Data
public class GoogleLoginRequestDTO {

    @NotBlank(message = "O token do Google é obrigatório.")
    private String credential; //token enviado pelo google após o usuário escolher a conta
}