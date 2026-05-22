package br.com.ifba.prg04pizzly.usuarios.service;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import br.com.ifba.prg04pizzly.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service //define a classe como camada de serviço
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService{

    //injeção de dependência do repository
    private final UsuarioRepository usuarioRepository;

    //cria logs da aplicação
    private static final Logger log =
            LoggerFactory.getLogger(UsuarioService.class); //logs

    //salva os usuarios no banco de dados
    @Override
    public Usuario save(Usuario usuario) {

        if (usuario == null) {
            throw new RuntimeException(
                    "Dados do usuário não preenchidos");
        }

        log.info("Salvando usuário");

        return usuarioRepository.save(usuario);
    }

    //listar todos os usuarios cadastrados
    @Override
    public List<Usuario> findAll() {

        log.info("Listando usuários");

        return usuarioRepository.findAll();
    }

    // buscar usuario pelo id
    @Override
    public Usuario findById(Long id) {

        log.info("Buscando usuário por ID");

        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // atualizar usuario pelo id
    @Override
    public Usuario update(Long id, Usuario usuarioAtualizado) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());

        log.info("Atualizando usuário");


        return usuarioRepository.save(usuario);
    }

    // deletar usuario pelo id
    @Override
    public void delete(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        log.info("Deletando usuário");
        usuarioRepository.delete(usuario);
    }
}
