package br.com.ifba.prg04pizzly.verificacaoemail.repository;

import br.com.ifba.prg04pizzly.verificacaoemail.entity.CodigoVerificacao;
import br.com.ifba.prg04pizzly.verificacaoemail.entity.enums.TipoVerificacaoEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Repositório responsável pelas operações de persistência dos códigos de verificação
public interface CodigoVerificacaoRepository
        extends JpaRepository<CodigoVerificacao, Long> {

    // busca o código mais recente, ainda não utilizado, para o e-mail e tipo informados
    Optional<CodigoVerificacao> findTopByEmailAndTipoAndUsadoFalseOrderByIdDesc(
            String email,
            TipoVerificacaoEmail tipo
    );
}