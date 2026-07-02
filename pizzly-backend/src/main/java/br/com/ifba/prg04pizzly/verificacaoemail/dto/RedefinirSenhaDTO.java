package br.com.ifba.prg04pizzly.verificacaoemail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//DTO usado para validar código e redefinir senha.
@Data
public class RedefinirSenhaDTO {

    @NotBlank(message = "o e-mail é obrigatório")
    @Email(message = "e-mail inválido")
    private String email;

    @NotBlank(message = "o código é obrigatório")
    private String codigo;

    @NotBlank(message = "a nova senha é obrigatória")
    private String novaSenha;
}