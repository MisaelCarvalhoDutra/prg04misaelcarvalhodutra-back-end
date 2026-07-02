package br.com.ifba.prg04pizzly.verificacaoemail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//DTO usado para solicitar envio de código por e-mail.
@Data
public class EnviarCodigoDTO {

    @NotBlank(message = "o e-mail é obrigatório")
    @Email(message = "e-mail inválido")
    private String email;
}

//Esse DTO serve apenas para receber email
//quando o usuário clicar em Enviar código