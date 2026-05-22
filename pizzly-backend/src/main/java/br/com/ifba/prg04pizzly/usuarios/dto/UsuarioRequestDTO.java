package br.com.ifba.prg04pizzly.usuarios.dto;

import lombok.Data;

//UsuarioRequestDTO: dados que chegam

// DTO de requisição de usuário
// Representa os dados enviados pelo cliente quando cria ou atualiza um usuário
@Data
public class UsuarioRequestDTO {

    private String nome;

    private String email;

    private String senha;
}
