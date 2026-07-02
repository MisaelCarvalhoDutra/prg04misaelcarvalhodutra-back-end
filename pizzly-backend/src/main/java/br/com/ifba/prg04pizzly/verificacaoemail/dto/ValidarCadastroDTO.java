package br.com.ifba.prg04pizzly.verificacaoemail.dto;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//DTO usado para validar o código e criar a conta do cliente.
@Data
public class ValidarCadastroDTO {

    @NotBlank(message = "o e-mail é obrigatório")
    @Email(message = "e-mail inválido")
    private String email;

    @NotBlank(message = "o código é obrigatório")
    private String codigo;

    @NotNull(message = "os dados do cliente são obrigatórios")
    private ClienteRequestDTO cliente;
}