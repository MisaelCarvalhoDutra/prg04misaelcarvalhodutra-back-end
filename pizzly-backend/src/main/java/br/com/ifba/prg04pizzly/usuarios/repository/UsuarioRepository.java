package br.com.ifba.prg04pizzly.usuarios.repository;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
