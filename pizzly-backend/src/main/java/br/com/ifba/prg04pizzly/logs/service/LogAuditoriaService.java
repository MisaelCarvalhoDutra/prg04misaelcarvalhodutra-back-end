package br.com.ifba.prg04pizzly.logs.service;

import br.com.ifba.prg04pizzly.logs.dto.LogAuditoriaResponseDTO;
import br.com.ifba.prg04pizzly.logs.entity.LogAuditoria;
import br.com.ifba.prg04pizzly.logs.repository.LogAuditoriaRepository;
import br.com.ifba.prg04pizzly.usuarios.entity.Funcionario;
import br.com.ifba.prg04pizzly.funcionarios.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//esse é o Service responsável por registrar e consultar logs de auditoria
@Service
@RequiredArgsConstructor
public class LogAuditoriaService implements LogAuditoriaIService{

    // repositório responsável pela persistência dos logs
    private final LogAuditoriaRepository logAuditoriaRepository;

    // repositório responsável pela busca do funcionário que realizou a ação
    private final FuncionarioRepository funcionarioRepository;


    //cria um registro de auditoria para uma ação realizada no sistema
    public void registrar(
            Long funcionarioId,
            String acao,
            String entidade,
            Long entidadeId,
            String descricao
    ) {
        // busca o funcionário responsável pela ação
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElse(null);

        // cria o registro de auditoria com os dados da ação realizada
        LogAuditoria log = new LogAuditoria();

        log.setFuncionario(funcionario);
        log.setAcao(acao);
        log.setEntidade(entidade);
        log.setEntidadeId(entidadeId);
        log.setDescricao(descricao);
        log.setDataHora(LocalDateTime.now());

        logAuditoriaRepository.save(log);
    }

    //retorna os logs cadastrados de forma paginada
    public Page<LogAuditoriaResponseDTO> findAll(Pageable pageable) {
        // busca todos os logs de forma paginada e converte para DTO de resposta
        return logAuditoriaRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    //converte a entidade de log para DTO de resposta.
    private LogAuditoriaResponseDTO converterParaResponseDTO(LogAuditoria log) {
        LogAuditoriaResponseDTO dto = new LogAuditoriaResponseDTO();

        dto.setId(log.getId());
        dto.setAcao(log.getAcao());
        dto.setEntidade(log.getEntidade());
        dto.setEntidadeId(log.getEntidadeId());
        dto.setDescricao(log.getDescricao());
        dto.setDataHora(log.getDataHora());

        // adiciona os dados do funcionário, caso exista vínculo com o log
        if (log.getFuncionario() != null) {
            dto.setFuncionarioId(log.getFuncionario().getId());
            dto.setFuncionarioNome(log.getFuncionario().getNome());
            dto.setFuncionarioPerfil(log.getFuncionario().getPerfil().name());
        }

        return dto;
    }
}