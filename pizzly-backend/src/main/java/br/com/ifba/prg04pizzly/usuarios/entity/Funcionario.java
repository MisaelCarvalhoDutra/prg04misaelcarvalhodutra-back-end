package br.com.ifba.prg04pizzly.usuarios.entity;

import br.com.ifba.prg04pizzly.usuarios.entity.enums.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

//representa um funcionário do sistema
//Pode assumir diferentes perfis de acesso, como por exemplo: Administrador, Atendente ou Entregador
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Funcionario extends Usuario {

    private String matricula;

    @Enumerated(EnumType.STRING) //enum que define o Perfil desse funcionario
    private Perfil perfil;
}