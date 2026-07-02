package br.com.ifba.prg04pizzly.enderecos.service;

import br.com.ifba.prg04pizzly.enderecos.dto.EnderecoRequestDTO;
import br.com.ifba.prg04pizzly.enderecos.dto.EnderecoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//interface responsável pelos serviços de endereço, define as operações disponíveis para a camada de serviço.
public interface EnderecoIService {

    // cadastra um novo endereço
    EnderecoResponseDTO save(EnderecoRequestDTO enderecoDTO);

    // lista todos os endereços com paginação
    Page<EnderecoResponseDTO> findAll(Pageable pageable);

    // busca um endereço pelo id
    EnderecoResponseDTO findById(Long id);

    // lista todos os endereços de um cliente específico
    List<EnderecoResponseDTO> findByClienteId(Long clienteId);

    // atualiza os dados de um endereço
    EnderecoResponseDTO update(Long id, EnderecoRequestDTO enderecoDTO);

    // remove um endereço pelo id
    void delete(Long id);

    // define um endereço como principal
    EnderecoResponseDTO definirPrincipal(Long id);
}