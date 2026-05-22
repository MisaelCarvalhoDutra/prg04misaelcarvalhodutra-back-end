package br.com.ifba.prg04pizzly.usuarios.controller;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import br.com.ifba.prg04pizzly.usuarios.service.UsuarioIService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//controller responsável pelos endpoints de usuário
@RestController //transforma a classe em uma API REST
@RequestMapping("/usuarios") //define a rota principal
@RequiredArgsConstructor
public class UsuarioController implements UsuarioIController {

    //injeção de dependência do service
    private final UsuarioIService usuarioService;

    // cadastrar usuario - o Jackson converte automaticamente o JSON recebido em um objeto Usuario
    @Override
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {

        Usuario novoUsuario = usuarioService.save(usuario);

        //retorna status HTTP 201 com o usuario salvo
        return ResponseEntity.status(201).body(novoUsuario); // o objeto Usuario é convertido automaticamente para JSON na resposta da API
    }

    // listar usuarios
    @Override
    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {

        List<Usuario> usuarios = usuarioService.findAll();

        //retorna lista de usuarios
        return ResponseEntity.ok(usuarios); // os dados da lista são convertidos automaticamente para JSON pelo Jackson
    }

    // buscar usuario por id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {

        Usuario usuario = usuarioService.findById(id);

        return ResponseEntity.ok(usuario);
    }

    // atualizar usuario
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        Usuario usuarioAtualizado =
                usuarioService.update(id, usuario);

        //retorna usuario atualizado com status HTTP 200
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // deletar usuario
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        usuarioService.delete(id);

        //retorna status HTTP 204 sem conteúdo
        return ResponseEntity.noContent().build();
    }
}