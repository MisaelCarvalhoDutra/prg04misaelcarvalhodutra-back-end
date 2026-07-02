package br.com.ifba.prg04pizzly.funcionarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//esse DTO usado para atualização dos dados básicos do funcionário.
//não altera senha, matrícula ou perfil.
@Data
public class FuncionarioUpdateDTO {

    @NotBlank(message = "o nome é obrigatório")
    private String nome;

    @NotBlank(message = "o email é obrigatório")
    @Email(message = "email inválido")
    private String email;

    private String telefone;
}