package br.com.ifba.prg04pizzly.usuarios.controller;

import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioRequestDTO;
import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioResponseDTO;

import br.com.ifba.prg04pizzly.usuarios.service.UsuarioIService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ifba.prg04pizzly.client.client.ViaCepClient;


// Controller responsável pelos endpoints de usuário
// Recebe DTOs das requisições e retorna DTOs nas respostas
@RestController //transforma a classe em uma API REST
@RequestMapping("/usuarios") //define a rota principal
@RequiredArgsConstructor
public class UsuarioController implements UsuarioIController {

    //injeção de dependência do service
    private final UsuarioIService usuarioService;

    //injeção de dependencia do client
    private final ViaCepClient viaCepClient;


    // cadastrar usuario - o Jackson converte automaticamente o JSON recebido em um objeto Usuario
    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> save(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {

        UsuarioResponseDTO novoUsuario = usuarioService.save(usuarioDTO);

        //retorna status HTTP 201 com o usuario salvo
        return ResponseEntity.status(201).body(novoUsuario); // o objeto Usuario é convertido automaticamente para JSON na resposta da API
    }

    //lista usuários utilizando paginação
    @Override
    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> findAll(Pageable pageable) {

        //recebe lista paginada de usuários do service
        Page<UsuarioResponseDTO> usuarios =
                usuarioService.findAll(pageable);

        //retorna lista paginada de usuarios
        return ResponseEntity.ok(usuarios);
    }

    // endpoint utilizado pelo WebClient
    @GetMapping("/findall")
    public ResponseEntity<Page<UsuarioResponseDTO>> findAllWebClient(
            Pageable pageable) {

        return ResponseEntity.ok(
                usuarioService.findAll(pageable)
        );
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
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id,@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {

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

    // endpoint responsável por consumir API externa ViaCEP
    @GetMapping("/cep/{cep}")
    public ResponseEntity<String> buscarCep(@PathVariable String cep) {

        return ResponseEntity.ok(
                viaCepClient.buscarCep(cep)
        );
    }
}