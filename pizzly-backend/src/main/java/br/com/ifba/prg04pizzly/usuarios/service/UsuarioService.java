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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import org.springframework.transaction.annotation.Transactional;


// Recebe UsuarioRequestDTO e retorna UsuarioResponseDTO

// Implementação das regras de negócio de usuário
// Realiza conversão entre DTOs e entidades usando ModelMapper
// Implementa tratamento de exceções e validações
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
    @Transactional
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

    //lista usuários utilizando paginação do Spring Data
    @Override
    public Page<UsuarioResponseDTO> findAll(Pageable pageable){

        log.info("Listando usuários");

        //busca usuários paginados e converte Entity para DTO
        return usuarioRepository.findAll(pageable)
                .map(usuario ->
                        modelMapper.map(usuario, UsuarioResponseDTO.class));
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
    @Transactional
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
    @Transactional
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        log.info("Deletando usuário");
        usuarioRepository.delete(usuario);
    }
}
