package br.com.ifba.prg04pizzly.funcionarios.service;

import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioRequestDTO;
import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioResponseDTO;
import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Interface responsável pelos serviços de funcionário.
public interface FuncionarioIService {

    // cadastra um novo funcionário e registra o responsável pela ação
    FuncionarioResponseDTO save(
            FuncionarioRequestDTO funcionarioDTO,
            Long funcionarioId);

    // listar funcionários com paginação
    Page<FuncionarioResponseDTO> findAll(Pageable pageable);

    // buscar funcionário pelo id
    FuncionarioResponseDTO findById(Long id);

    // atualiza os dados básicos do funcionário e registra o responsável pela ação
    FuncionarioResponseDTO update(
            Long id,
            FuncionarioUpdateDTO funcionarioDTO,
            Long funcionarioId);

    // remove um funcionário e registra o responsável pela ação
    void delete(
            Long id,
            Long funcionarioId);

    // altera o status ativo/inativo do funcionário (na aba admin no front)
    FuncionarioResponseDTO alterarStatus(
            Long id,
            Boolean ativo,
            Long funcionarioId);
}