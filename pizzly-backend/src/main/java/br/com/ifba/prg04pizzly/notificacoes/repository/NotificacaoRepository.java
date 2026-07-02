package br.com.ifba.prg04pizzly.notificacoes.repository;

import br.com.ifba.prg04pizzly.notificacoes.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//repositório responsável pelas operações de persistência da entidade Notificação
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    // lista todas as notificações de um cliente específico
    List<Notificacao> findByClienteId(Long clienteId);

    // lista apenas as notificações não lidas de um cliente específico
    List<Notificacao> findByClienteIdAndLidaFalse(Long clienteId);
}