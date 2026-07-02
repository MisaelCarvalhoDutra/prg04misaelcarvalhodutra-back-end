package br.com.ifba.prg04pizzly.clientes.controller;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteRequestDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//Interface dos endpoints de cliente.
//Define o contrato entre o controller e a API utilizando DTOs , contrato das operações HTTP disponíveis para clientes.
public interface ClienteIController {

    // cadastra um novo cliente
    ResponseEntity<ClienteResponseDTO> save(ClienteRequestDTO clienteDTO);

    // lista clientes com paginação
    ResponseEntity<Page<ClienteResponseDTO>> findAll(Pageable pageable);

    // busca um cliente pelo id
    ResponseEntity<ClienteResponseDTO> findById(Long id);

    // atualiza os dados de um cliente pelo id
    ResponseEntity<ClienteResponseDTO> update(
            Long id,
            ClienteUpdateDTO clienteDTO
    );

    // remove um cliente pelo id
    ResponseEntity<Void> delete(Long id);
}