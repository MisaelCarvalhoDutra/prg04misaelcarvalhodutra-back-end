package br.com.ifba.prg04pizzly.verificacaoemail.entity;

import br.com.ifba.prg04pizzly.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04pizzly.verificacaoemail.entity.enums.TipoVerificacaoEmail;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// representa um código temporário enviado por e-mail para validar
// cadastro de clientes ou recuperação de senha
@Entity
@Data
public class CodigoVerificacao extends PersistenceEntity {

    // e-mail que receberá o código de verificação
    @Column(nullable = false)
    private String email;

    // código numérico enviado ao usuário (6 digitos)
    @Column(nullable = false)
    private String codigo;

    // define se o código será utilizado para
    // CADASTRO ou RECUPERAÇÃO DE SENHA
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVerificacaoEmail tipo;

    // data e hora limite para utilização do código, codigo expira
    @Column(nullable = false)
    private LocalDateTime expiraEm;

    // indica se o código já foi utilizado pelo usuário
    @Column(nullable = false)
    private Boolean usado;
}