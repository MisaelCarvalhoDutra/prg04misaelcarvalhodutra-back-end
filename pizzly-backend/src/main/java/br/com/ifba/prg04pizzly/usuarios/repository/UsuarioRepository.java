package br.com.ifba.prg04pizzly.usuarios.repository;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // validação de email duplicado
    boolean existsByEmail(String email);

    // busca usuário pelo email para autenticação
    Optional<Usuario> findByEmail(String email);
}