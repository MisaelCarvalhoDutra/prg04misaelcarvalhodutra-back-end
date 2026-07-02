package br.com.ifba.prg04pizzly.clientes.service;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteRequestDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteUpdateDTO;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ifba.prg04pizzly.clientes.repository.ClienteRepository;

import java.time.LocalDateTime;

//Implementação das regras de negócio relacionadas aos clientes.
@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteIService {

    // repositório responsável pela persistência dos clientes
    private final ClienteRepository clienteRepository;

    // responsável pela conversão entre DTOs e entidades
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ClienteResponseDTO save(ClienteRequestDTO clienteDTO) {

        // valida se os dados foram informados
        if (clienteDTO == null) {
            throw new BusinessException("Dados do cliente não preenchidos");
        }

        // valida email duplicado
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        // converte DTO para entidade
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);

        // define informações automáticas do cadastro
        cliente.setDataCadastro(LocalDateTime.now());
        cliente.setAtivo(true);

        Cliente salvo = clienteRepository.save(cliente);

        // converte entidade para DTO de resposta
        return modelMapper.map(salvo, ClienteResponseDTO.class);
    }

    @Override
    public Page<ClienteResponseDTO> findAll(Pageable pageable) {
        // busca todos os clientes de forma paginada e converte para DTO de resposta
        return clienteRepository.findAll(pageable)
                .map(cliente ->
                        modelMapper.map(cliente, ClienteResponseDTO.class));
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        // busca cliente pelo id
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteUpdateDTO clienteDTO) {

        // busca o cliente que será atualizado
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // impede que o cliente altere o e-mail para um e-mail já usado por outro cliente
        if (clienteRepository.existsByEmailAndIdNot(clienteDTO.getEmail(), id)) {
            throw new BusinessException("Email já cadastrado para outro cliente");
        }

        // atualiza os dados do cliente
        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setEmail(clienteDTO.getEmail());
        clienteExistente.setTelefone(clienteDTO.getTelefone());

        Cliente atualizado = clienteRepository.save(clienteExistente);

        return modelMapper.map(atualizado, ClienteResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // busca o cliente antes de remover para garantir que ele existe
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        clienteRepository.delete(cliente);
    }
}