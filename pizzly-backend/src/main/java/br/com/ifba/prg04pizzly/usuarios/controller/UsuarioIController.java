package br.com.ifba.prg04pizzly.usuarios.controller;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import org.springframework.http.ResponseEntity;

import java.util.List;

// Interface dos endpoints de Usuario
public interface UsuarioIController {

    // salvar usuário
    ResponseEntity<Usuario> save(Usuario usuario);

    // listar todos os usuários
    ResponseEntity<List<Usuario>> findAll();

    // buscar usuário pelo id
    ResponseEntity<Usuario> findById(Long id);

    // atualizar usuário pelo id
    ResponseEntity<Usuario> update(Long id, Usuario usuario);

    // deletar usuário pelo id
    ResponseEntity<Void> delete(Long id);
}