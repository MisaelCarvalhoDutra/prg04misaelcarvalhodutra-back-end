package br.com.ifba.prg04pizzly.usuarios.service;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;

import java.util.List;

//interface responsável pelos serviços de usuário
public interface UsuarioIService {

    //salvar usuario
    Usuario save(Usuario usuario);

    //listar usuario
    List<Usuario> findAll();

    //fazer a busca de usuário por id
    Usuario findById(Long id);

    //atualizar usuario
    Usuario update(Long id, Usuario usuario);

    //deletar usuario
    void delete(Long id);
}
