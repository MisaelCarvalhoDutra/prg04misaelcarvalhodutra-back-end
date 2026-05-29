package br.com.ifba.prg04pizzly.usuarios.service;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;

import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioRequestDTO;
import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


//interface responsável pelos serviços de usuário
// Define os métodos que a camada de serviço deve implementar, seguindo o padrão DTO
public interface UsuarioIService {

    // Salva um novo usuário e retorna os dados de resposta
    UsuarioResponseDTO save(UsuarioRequestDTO usuarioDTO);

    //lista usuários de forma paginada
    Page<UsuarioResponseDTO> findAll(Pageable pageable);

    //fazer a busca de usuário por id
    UsuarioResponseDTO findById(Long id);

    //atualizar usuario existente
    UsuarioResponseDTO update(Long id,
                              UsuarioRequestDTO usuarioDTO);

    //deletar usuario pelo id
    void delete(Long id);
}
