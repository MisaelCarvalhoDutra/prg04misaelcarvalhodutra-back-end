package br.com.ifba.prg04pizzly.usuarios.controller;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import br.com.ifba.prg04pizzly.usuarios.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //transforma a classe em uma API REST
@RequestMapping("/usuarios") //define a rota principal
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // cadastrar usuario
    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {

        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);

        //retorna status HTTP 200 com o usuario salvo
        return ResponseEntity.status(201).body(novoUsuario);
    }

    // listar usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        //retorna lista de usuarios com status HTTP 200
        return ResponseEntity.ok(usuarios);
    }

    // buscar usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {

        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        return ResponseEntity.ok(usuario);
    }

    // atualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        Usuario usuarioAtualizado =
                usuarioService.atualizarUsuario(id, usuario);

        //retorna usuario atualizado com status HTTP 200
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // deletar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {

        usuarioService.deletarUsuario(id);

        //retorna status HTTP 204 sem conteúdo
        return ResponseEntity.noContent().build();
    }
}