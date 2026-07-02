package br.com.ifba.prg04pizzly.auth.dto;

import lombok.Data;

//aqui é o DTO utilizado para retornar/devolver ao front end os dados básicos do usuario autenticado
@Data
public class LoginResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String tipo; //identifica se é CLIENTE ou FUNCIONARIO
    private String perfil; //detalha o nível de acesso
}