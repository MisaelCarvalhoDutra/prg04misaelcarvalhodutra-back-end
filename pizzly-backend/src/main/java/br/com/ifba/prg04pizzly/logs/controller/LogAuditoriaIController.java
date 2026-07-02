package br.com.ifba.prg04pizzly.logs.controller;

import br.com.ifba.prg04pizzly.logs.dto.LogAuditoriaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//Interface dos endpoints de logs de auditoria, define o contrato das operações HTTP disponíveis para consulta dos logs
public interface LogAuditoriaIController {

    //lista os logs de auditoria
    ResponseEntity<Page<LogAuditoriaResponseDTO>> findAll(Pageable pageable);
}