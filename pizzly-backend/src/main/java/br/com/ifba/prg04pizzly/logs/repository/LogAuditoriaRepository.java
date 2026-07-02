package br.com.ifba.prg04pizzly.logs.repository;

import br.com.ifba.prg04pizzly.logs.entity.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repositório responsável pelas operações de persistência da entidade LogAuditoria
@Repository
public interface LogAuditoriaRepository
        extends JpaRepository<LogAuditoria, Long> {
}