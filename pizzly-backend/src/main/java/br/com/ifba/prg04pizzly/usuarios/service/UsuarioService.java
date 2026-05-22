package br.com.ifba.prg04pizzly.usuarios.service;

import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import br.com.ifba.prg04pizzly.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioRequestDTO;
import br.com.ifba.prg04pizzly.usuarios.dto.UsuarioResponseDTO;

import java.util.List;

import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;

// Recebe UsuarioRequestDTO e retorna UsuarioResponseDTO

// Implementação do serviço de usuário
// Converte entre DTOs e entidades usando ModelMapper
// Implementa as regras de negócio e tratamento de exceções
@Service //define a classe como camada de serviço
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService{

    //Repositorio de persistencia
    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper; // Mapper para conversão DTO para Entity e vice-versa

    //cria logs da aplicação
    private static final Logger log =
            LoggerFactory.getLogger(UsuarioService.class); //logs

    //salva os usuarios no banco de dados
    @Override
    public UsuarioResponseDTO save(UsuarioRequestDTO usuarioDTO) {

        if (usuarioDTO == null) {
            throw new BusinessException(
                    "Dados do usuário não preenchidos");
        }

        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        log.info("Salvando usuário");

        // converte DTO para Entity
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        Usuario salvo = usuarioRepository.save(usuario);

        // converte Entity para DTO de resposta
        return modelMapper.map(salvo, UsuarioResponseDTO.class);
    }

    //listar todos os usuarios cadastrados
    @Override
    public List<UsuarioResponseDTO> findAll() {

        log.info("Listando usuários");

        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class))
                .toList();
    }

    // buscar usuario pelo id
    @Override
    public UsuarioResponseDTO findById(Long id) {

        log.info("Buscando usuário por ID");

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    // atualizar usuario pelo id
    @Override
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO usuarioDTO) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setSenha(usuarioDTO.getSenha());

        log.info("Atualizando usuário");


        Usuario atualizado = usuarioRepository.save(usuarioExistente);
        return modelMapper.map(atualizado, UsuarioResponseDTO.class);
    }

    // deletar usuario pelo id
    @Override
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        log.info("Deletando usuário");
        usuarioRepository.delete(usuario);
    }
}
