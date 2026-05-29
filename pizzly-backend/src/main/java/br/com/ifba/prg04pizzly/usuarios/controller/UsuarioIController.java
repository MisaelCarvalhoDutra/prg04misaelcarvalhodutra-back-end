package br.com.ifba.prg04pizzly.usuarios.controller;

import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioRequestDTO;
import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioResponseDTO;

import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Agora recebe: UsuarioRequestDTO
//porque é o dado vindo da requisição.

//Agora retorna: UsuarioResponseDTO porque é o dado devolvido pela API.


// Interface dos endpoints de usuário
// Define o contrato entre o controller e a API usando DTOs
public interface UsuarioIController {

    // salvar usuário
    ResponseEntity<UsuarioResponseDTO> save(
            UsuarioRequestDTO usuarioDTO);

    //lista usuários com paginação
    ResponseEntity<Page<UsuarioResponseDTO>> findAll(Pageable pageable);

    // buscar usuário pelo id
    ResponseEntity<UsuarioResponseDTO> findById(Long id);

    // atualizar usuário pelo id
    ResponseEntity<UsuarioResponseDTO> update(
            Long id,
            UsuarioRequestDTO usuarioDTO);

    // deletar usuário pelo id
    ResponseEntity<Void> delete(Long id);
}