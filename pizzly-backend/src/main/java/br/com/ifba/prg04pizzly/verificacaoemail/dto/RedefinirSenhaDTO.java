package br.com.ifba.prg04pizzly.verificacaoemail.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO utilizado para redefinir a senha por meio de um token temporário.
@Data
public class RedefinirSenhaDTO {

    @NotBlank(message = "o token é obrigatório")
    private String token;

    @NotBlank(message = "a nova senha é obrigatória")
    @Size(
            min = 8,
            message = "a nova senha deve possuir no mínimo 8 caracteres"
    )
    private String novaSenha;
}