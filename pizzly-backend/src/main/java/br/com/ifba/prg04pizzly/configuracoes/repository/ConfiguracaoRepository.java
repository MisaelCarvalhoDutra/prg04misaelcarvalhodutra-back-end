package br.com.ifba.prg04pizzly.configuracoes.repository;

import br.com.ifba.prg04pizzly.configuracoes.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositório responsável pelas operações de persistência da entidade Configuracao.
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
}