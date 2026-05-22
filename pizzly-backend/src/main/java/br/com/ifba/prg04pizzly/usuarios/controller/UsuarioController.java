package br.com.ifba.prg04pizzly.usuarios.controller;

import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioRequestDTO;
import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioResponseDTO;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import br.com.ifba.prg04pizzly.usuarios.service.UsuarioIService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller responsável pelos endpoints de usuário
// Recebe DTOs das requisições e retorna DTOs nas respostas
@RestController //transforma a classe em uma API REST
@RequestMapping("/usuarios") //define a rota principal
@RequiredArgsConstructor
public class UsuarioController implements UsuarioIController {

    //injeção de dependência do service
    private final UsuarioIService usuarioService;

    // cadastrar usuario - o Jackson converte automaticamente o JSON recebido em um objeto Usuario
    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> save(@RequestBody UsuarioRequestDTO usuarioDTO) {

        UsuarioResponseDTO novoUsuario = usuarioService.save(usuarioDTO);

        //retorna status HTTP 201 com o usuario salvo
        return ResponseEntity.status(201).body(novoUsuario); // o objeto Usuario é convertido automaticamente para JSON na resposta da API
    }

    // listar usuarios
    @Override
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {

        List<UsuarioResponseDTO> usuarios = usuarioService.findAll();

        //retorna lista de usuarios
        return ResponseEntity.ok(usuarios); // os dados da lista são convertidos automaticamente para JSON pelo Jackson
    }

    // buscar usuario por id
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {

        UsuarioResponseDTO usuario = usuarioService.findById(id);

        return ResponseEntity.ok(usuario);
    }

    // atualizar usuario
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO usuarioDTO) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.update(id, usuarioDTO);

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