package br.com.ifba.prg04pizzly.usuarios.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

//representa o cliente da pizzaria.
//ou seja, é um usuario responsável por realizar pedidos e possuir endereços.
@Entity
@Data
@EqualsAndHashCode(callSuper = true) //herda da super classe os atributos
public class Cliente extends Usuario {

}