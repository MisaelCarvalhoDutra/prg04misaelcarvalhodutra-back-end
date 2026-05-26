package br.com.ifba.prg04pizzly.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//UsuarioRequestDTO: dados que chegam

// DTO de requisição de usuário
// Representa os dados enviados pelo cliente quando cria ou atualiza um usuário
@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "o nome é obrigatório")
    private String nome;

    @NotBlank(message = "o email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "a senha é obrigatória")
    @Size(min = 6, message = "a senha deve ter no mínimo 6 caracteres")
    private String senha;
}
