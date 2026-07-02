package br.com.ifba.prg04pizzly.logs.controller;

import br.com.ifba.prg04pizzly.logs.dto.LogAuditoriaResponseDTO;
import br.com.ifba.prg04pizzly.logs.service.LogAuditoriaIService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//controller responsável pela consulta dos logs de auditoria
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogAuditoriaController implements LogAuditoriaIController {

    // service responsável pelas regras de consulta dos logs de auditoria
    private final LogAuditoriaIService logAuditoriaService;

    //lista os logs registrados no sistema
    @Override
    @GetMapping
    public ResponseEntity<Page<LogAuditoriaResponseDTO>> findAll(
            Pageable pageable) {

        // busca todos os logs registrados no sistema
        Page<LogAuditoriaResponseDTO> logs =
                logAuditoriaService.findAll(pageable);

        return ResponseEntity.ok(logs);
    }
}