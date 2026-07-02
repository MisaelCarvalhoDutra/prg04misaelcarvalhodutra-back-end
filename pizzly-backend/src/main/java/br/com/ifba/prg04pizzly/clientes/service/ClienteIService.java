package br.com.ifba.prg04pizzly.clientes.service;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteRequestDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//interface responsável por definir as operações de negócio relacionadas a clientes.
 //Define as operações disponíveis para a camada de serviço.
public interface ClienteIService {

    // cadastra um novo cliente
    ClienteResponseDTO save(ClienteRequestDTO clienteDTO);

    // lista clientes com paginação
    Page<ClienteResponseDTO> findAll(Pageable pageable);

    // busca um cliente pelo id
    ClienteResponseDTO findById(Long id);

    // atualiza os dados cadastrais de um cliente
    ClienteResponseDTO update(Long id, ClienteUpdateDTO clienteDTO);

    // remove um cliente pelo id
    void delete(Long id);
}