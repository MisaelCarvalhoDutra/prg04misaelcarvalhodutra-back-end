package br.com.ifba.prg04pizzly.funcionarios.service;

import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioRequestDTO;
import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioResponseDTO;
import br.com.ifba.prg04pizzly.funcionarios.dto.FuncionarioUpdateDTO;
import br.com.ifba.prg04pizzly.funcionarios.repository.FuncionarioRepository;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.usuarios.entity.Funcionario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ifba.prg04pizzly.logs.service.LogAuditoriaIService;


import java.time.LocalDateTime;

//Implementação das regras de negócio de funcionário
@Service
@RequiredArgsConstructor
public class FuncionarioService implements FuncionarioIService {

    // repositório responsável pela persistência dos funcionários
    private final FuncionarioRepository funcionarioRepository;

    // responsável pela conversão entre DTOs e entidades
    private final ModelMapper modelMapper;

    // service responsável pelo registro de logs administrativos
    private final LogAuditoriaIService logAuditoriaService;

    @Override
    @Transactional
    public FuncionarioResponseDTO save(
            FuncionarioRequestDTO funcionarioDTO,
            Long funcionarioId) {

        // valida se os dados foram informados
        if (funcionarioDTO == null) {
            throw new BusinessException("Dados do funcionário não preenchidos");
        }

        // impede cadastro com e-mail já utilizado
        if (funcionarioRepository.existsByEmail(funcionarioDTO.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        // impede cadastro com matrícula já utilizada
        if (funcionarioRepository.existsByMatricula(funcionarioDTO.getMatricula())) {
            throw new BusinessException("Matrícula já cadastrada");
        }

        // converte DTO para entidade
        Funcionario funcionario = modelMapper.map(funcionarioDTO, Funcionario.class);

        funcionario.setDataCadastro(LocalDateTime.now());
        funcionario.setAtivo(true);

        Funcionario salvo = funcionarioRepository.save(funcionario);

        // registra a criação do funcionário no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "CRIAR",
                "FUNCIONARIO",
                salvo.getId(),
                "Funcionário " + salvo.getNome() + " cadastrado"
        );

        return modelMapper.map(salvo, FuncionarioResponseDTO.class);
    }

    @Override
    public Page<FuncionarioResponseDTO> findAll(Pageable pageable) {

        // busca todos os funcionários de forma paginada e converte para DTO de resposta
        return funcionarioRepository.findAll(pageable)
                .map(funcionario ->
                        modelMapper.map(funcionario, FuncionarioResponseDTO.class));
    }

    @Override
    public FuncionarioResponseDTO findById(Long id) {

        // busca funcionário pelo id ou lança exceção caso não exista
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado")); //exceção

        return modelMapper.map(funcionario, FuncionarioResponseDTO.class);
    }

    @Override
    @Transactional
    public FuncionarioResponseDTO update(
            Long id,
            FuncionarioUpdateDTO funcionarioDTO,
            Long funcionarioId) {

        // valida se os dados foram informados
        if (funcionarioDTO == null) {
            throw new BusinessException("Dados do funcionário não preenchidos");
        }

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado"));

        if (funcionarioRepository.existsByEmailAndIdNot(funcionarioDTO.getEmail(), id)) {
            throw new BusinessException("Email já cadastrado para outro funcionário");
        }

        // atualiza apenas os dados básicos do perfil do funcionário
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setTelefone(funcionarioDTO.getTelefone());

        Funcionario atualizado = funcionarioRepository.save(funcionario);

        // registra a atualização do funcionário no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EDITAR",
                "FUNCIONARIO",
                atualizado.getId(),
                "Funcionário " + atualizado.getNome() + " atualizado"
        );

        return modelMapper.map(atualizado, FuncionarioResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(
            Long id,
            Long funcionarioId) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Funcionário não encontrado"));

        // guarda os dados antes da exclusão para registrar corretamente na auditoria
        String nomeFuncionario = funcionario.getNome();
        Long funcionarioRemovidoId = funcionario.getId();

        funcionarioRepository.delete(funcionario);

        // registra a remoção do funcionário no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EXCLUIR",
                "FUNCIONARIO",
                funcionarioRemovidoId,
                "Funcionário " + nomeFuncionario + " removido"
        );
    }


    //Ativa ou inativa um funcionário
    @Override
    @Transactional
    public FuncionarioResponseDTO alterarStatus(
            Long id,
            Boolean ativo,
            Long funcionarioId) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Funcionário não encontrado"));

        funcionario.setAtivo(ativo);

        Funcionario atualizado =
                funcionarioRepository.save(funcionario);

        // registra a alteração de status no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                ativo ? "ATIVAR" : "INATIVAR",
                "FUNCIONARIO",
                atualizado.getId(),
                "Funcionário "
                        + atualizado.getNome()
                        + (ativo ? " ativado" : " inativado")
        );

        return modelMapper.map(
                atualizado,
                FuncionarioResponseDTO.class);
    }
}