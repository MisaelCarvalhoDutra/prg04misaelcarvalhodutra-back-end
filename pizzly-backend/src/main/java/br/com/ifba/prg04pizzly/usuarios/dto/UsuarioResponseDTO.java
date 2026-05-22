package br.com.ifba.prg04pizzly.usuarios.dto;

import lombok.Data;

//UsuarioResponseDTO: dados que saem

// DTO de resposta de usuário
// Contém os dados que a API retorna para o cliente, sem expor informações sensíveis como senha
@Data
public class UsuarioResponseDTO {

    private Long id; //gerado pelo banco, pra identificar
    private String nome;
    private String email;
}
