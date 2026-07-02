package br.com.ifba.prg04pizzly.funcionarios.dto;

import br.com.ifba.prg04pizzly.usuarios.entity.enums.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

//DTO utilizado para receber os dados necessários para o cadastro de um novo funcionário
@Data
public class FuncionarioRequestDTO {

    @NotBlank(message = "o nome é obrigatório")
    private String nome;

    @NotBlank(message = "o email é obrigatório")
    @Email(message = "email inválido")
    private String email;

    private String telefone;

    @NotBlank(message = "a senha é obrigatória")
    @Size(min = 8, message = "a senha deve ter no mínimo 8 caracteres")
    private String senha;

    @NotBlank(message = "a matrícula é obrigatória")
    private String matricula;

    @NotNull(message = "o perfil é obrigatório")
    private Perfil perfil; //enum pra definir o perfil do funcionario
}