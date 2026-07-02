package br.com.ifba.prg04pizzly.logs.service;

import br.com.ifba.prg04pizzly.logs.dto.LogAuditoriaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//interface que define as operações de auditoria do sistema
public interface LogAuditoriaIService {

    //registra uma ação administrativa realizada por um funcionário,
    //armazenando a operação executada no histórico de auditoria
    void registrar(
            Long funcionarioId,
            String acao,
            String entidade,
            Long entidadeId,
            String descricao
    );

    //lista todos os registros de auditoria utilizando paginação
    Page<LogAuditoriaResponseDTO> findAll(Pageable pageable);
}