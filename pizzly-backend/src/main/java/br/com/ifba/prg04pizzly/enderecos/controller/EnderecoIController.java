package br.com.ifba.prg04pizzly.enderecos.controller;

import br.com.ifba.prg04pizzly.enderecos.dto.EnderecoRequestDTO;
import br.com.ifba.prg04pizzly.enderecos.dto.EnderecoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

//Interface dos endpoints de endereço, define o contrato das operações HTTP disponíveis para endereços
public interface EnderecoIController {

    // cadastra um novo endereço
    ResponseEntity<EnderecoResponseDTO> save(
            EnderecoRequestDTO enderecoDTO);

    // listar endereços com paginação
    ResponseEntity<Page<EnderecoResponseDTO>> findAll(
            Pageable pageable);

    // busca um endereço pelo id
    ResponseEntity<EnderecoResponseDTO> findById(
            Long id);

    // lista todos os endereços de um cliente
    ResponseEntity<List<EnderecoResponseDTO>> findByClienteId(
            Long clienteId);

    // atualiza os dados de um endereço
    ResponseEntity<EnderecoResponseDTO> update(
            Long id,
            EnderecoRequestDTO enderecoDTO);

    // remove um endereço pelo id
    ResponseEntity<Void> delete(
            Long id);

    // define um endereço como principal
    ResponseEntity<EnderecoResponseDTO> definirPrincipal(
            Long id);
}