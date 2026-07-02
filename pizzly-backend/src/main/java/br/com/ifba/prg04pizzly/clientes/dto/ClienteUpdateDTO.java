package br.com.ifba.prg04pizzly.clientes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


//DTO usado para atualização dos dados do cliente, ou sejaa,  editar os dados do perfil.
//ele não exige senha, pois alteração de senha deve ser feita separadamente.
@Data
public class ClienteUpdateDTO {

    @NotBlank(message = "o nome é obrigatório")
    private String nome;

    @NotBlank(message = "o email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    private String telefone;

}