package br.com.ifba.prg04pizzly.funcionarios.controller;

import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioRequestDTO;
import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioResponseDTO;
import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//Interface dos endpoints de funcionário.
//ela define o contrato entre o controller e a API utilizando DTOs, das operações HTTP disponíveis para funcionários
public interface FuncionarioIController {

    // cadastra um novo funcionário e informa quem realizou a ação
    ResponseEntity<FuncionarioResponseDTO> save(
            FuncionarioRequestDTO funcionarioDTO,
            Long funcionarioId);

    // listar funcionários com paginação
    ResponseEntity<Page<FuncionarioResponseDTO>> findAll(
            Pageable pageable);

    // buscar funcionário pelo id
    ResponseEntity<FuncionarioResponseDTO> findById(
            Long id);

    // atualiza os dados de um funcionário e informa quem realizou a ação
    ResponseEntity<FuncionarioResponseDTO> update(
            Long id,
            FuncionarioUpdateDTO funcionarioDTO,
            Long funcionarioId);

    // remove um funcionário e informa quem realizou a ação
    ResponseEntity<Void> delete(
            Long id,
            Long funcionarioId);

    // altera o status ativo/inativo do funcionário e informa quem realizou a ação
    ResponseEntity<FuncionarioResponseDTO> alterarStatus(
            Long id,
            Boolean ativo,
            Long funcionarioId);
}