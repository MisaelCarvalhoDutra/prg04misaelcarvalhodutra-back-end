package br.com.ifba.prg04pizzly.verificacaoemail.service;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteRequestDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;
import br.com.ifba.prg04pizzly.clientes.service.ClienteIService;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import br.com.ifba.prg04pizzly.usuarios.repository.UsuarioRepository;
import br.com.ifba.prg04pizzly.verificacaoemail.entity.CodigoVerificacao;
import br.com.ifba.prg04pizzly.verificacaoemail.entity.enums.TipoVerificacaoEmail;
import br.com.ifba.prg04pizzly.verificacaoemail.repository.CodigoVerificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import br.com.ifba.prg04pizzly.email.ResendEmailService;

import java.security.SecureRandom;
import java.time.LocalDateTime;

//Service responsável pelo envio e validação
//dos códigos de verificação enviados por e-mail
@Service
@RequiredArgsConstructor
public class VerificacaoEmailService implements VerificacaoEmailIService {

    private static final int TEMPO_EXPIRACAO_CODIGO_MINUTOS = 10;
    private static final int TEMPO_EXPIRACAO_TOKEN_MINUTOS = 15;
    private static final SecureRandom RANDOM = new SecureRandom();

    // responsável por enviar e-mails
    // responsável por enviar e-mails pela API do Resend
    private final ResendEmailService resendEmailService;


    @Value("${app.frontend.url}")
    private String frontendUrl;

    // repositório responsável pela persistência dos códigos de verificação
    private final CodigoVerificacaoRepository codigoRepository;

    // service utilizado para criar o cliente após a validação do código
    private final ClienteIService clienteService;

    // repositório utilizado para buscar usuários na recuperação de senha
    private final UsuarioRepository usuarioRepository;

    ///envia código para validar o e-mail antes do cadastro.
    @Override
    @Transactional
    public void enviarCodigoCadastro(String email) {
        enviarCodigo(
                email,
                TipoVerificacaoEmail.CADASTRO,
                "Código de verificação - Pizzly"
        );
    }

    //valida o código informado e cria a conta do cliente
    @Override
    @Transactional
    public ClienteResponseDTO validarCadastro(
            String email,
            String codigo,
            ClienteRequestDTO clienteDTO
    ) {
        validarCodigo(email, codigo, TipoVerificacaoEmail.CADASTRO);

        return clienteService.save(clienteDTO);
    }

    // Envia um link temporário para recuperação de senha.
    @Override
    @Transactional
    public void enviarLinkRecuperacaoSenha(String email) {

        // Verifica se existe usuário cadastrado com o e-mail informado.
        usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BusinessException("E-mail não encontrado"));

        // Gera um token único e difícil de adivinhar.
        String token = UUID.randomUUID().toString();

        CodigoVerificacao tokenRecuperacao = new CodigoVerificacao();

        tokenRecuperacao.setEmail(email);
        tokenRecuperacao.setCodigo(token);
        tokenRecuperacao.setTipo(
                TipoVerificacaoEmail.RECUPERACAO_SENHA
        );
        tokenRecuperacao.setExpiraEm(
                LocalDateTime.now()
                        .plusMinutes(TEMPO_EXPIRACAO_TOKEN_MINUTOS)
        );
        tokenRecuperacao.setUsado(false);

        codigoRepository.save(tokenRecuperacao);

        String conteudoEmail =
                "Olá!\n\n"
                        + "Seu token de recuperação é:\n\n"
                        + token
                        + "\n\nEste token expira em "
                        + TEMPO_EXPIRACAO_TOKEN_MINUTOS
                        + " minutos.\n\n"
                        + "Equipe Pizzly";

        resendEmailService.enviarEmail(
                email,
                "Token de verificação - Pizzly",
                conteudoEmail
        );
    }

    // Redefine a senha após validar o token recebido pelo link
    @Override
    @Transactional
    public void redefinirSenha(
            String token,
            String novaSenha
    ) {

        CodigoVerificacao tokenSalvo =
                codigoRepository
                        .findByCodigoAndTipoAndUsadoFalse(
                                token,
                                TipoVerificacaoEmail.RECUPERACAO_SENHA
                        )
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Link de recuperação inválido"
                                ));

        // Verifica se o prazo do token terminou.
        if (LocalDateTime.now().isAfter(tokenSalvo.getExpiraEm())) {
            throw new BusinessException(
                    "O link de recuperação expirou"
            );
        }

        // Localiza o usuário pelo e-mail associado ao token.
        Usuario usuario = usuarioRepository
                .findByEmail(tokenSalvo.getEmail())
                .orElseThrow(() ->
                        new BusinessException(
                                "Usuário não encontrado"
                        ));

        // Atualiza a senha.
        usuario.setSenha(novaSenha);

        usuarioRepository.save(usuario);

        // Invalida o token para impedir um segundo uso.
        tokenSalvo.setUsado(true);

        codigoRepository.save(tokenSalvo);
    }

    //gera um código aleatório, salva no banco e realiza o envio do e-mail
    // Gera um código numérico para validação do cadastro,
// salva no banco e envia ao usuário por e-mail.
    private void enviarCodigo(
            String email,
            TipoVerificacaoEmail tipo,
            String assunto
    ) {
        // gera o código temporário que será enviado ao usuário
        String codigo = gerarCodigo();

        // salva o código no banco com prazo de expiração e status de não utilizado
        CodigoVerificacao codigoVerificacao = new CodigoVerificacao();

        codigoVerificacao.setEmail(email);
        codigoVerificacao.setCodigo(codigo);
        codigoVerificacao.setTipo(tipo);
        codigoVerificacao.setExpiraEm(
                LocalDateTime.now()
                        .plusMinutes(TEMPO_EXPIRACAO_CODIGO_MINUTOS)
        );
        codigoVerificacao.setUsado(false);

        codigoRepository.save(codigoVerificacao);

        // monta a mensagem de e-mail com o código de verificação
        String conteudoEmail =
                "Olá!\n\n"
                        + "Seu código de verificação é:\n\n"
                        + codigo
                        + "\n\nEste código expira em "
                        + TEMPO_EXPIRACAO_CODIGO_MINUTOS
                        + " minutos.\n\n"
                        + "Equipe Pizzly";

        resendEmailService.enviarEmail(
                email,
                assunto,
                conteudoEmail
        );
    }

    //verifica se o código existe, ainda não expirou e corresponde ao informado pelo usuário
    private void validarCodigo(
            String email,
            String codigo,
            TipoVerificacaoEmail tipo
    ) {
        // busca o código mais recente ainda não utilizado para o e-mail e tipo informados
        CodigoVerificacao codigoSalvo =
                codigoRepository
                        .findTopByEmailAndTipoAndUsadoFalseOrderByIdDesc(
                                email,
                                tipo
                        )
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Código não solicitado"
                                ));

        if (LocalDateTime.now().isAfter(codigoSalvo.getExpiraEm())) {
            throw new BusinessException("Código expirado");
        }

        // compara o código informado pelo usuário com o código salvo
        if (!codigoSalvo.getCodigo().equals(codigo)) {
            throw new BusinessException("Código inválido");
        }

        // impede reutilização do mesmo código
        codigoSalvo.setUsado(true);

        codigoRepository.save(codigoSalvo);
    }

    //gera código numérico de 6 dígitos
    private String gerarCodigo() {
        int numero = RANDOM.nextInt(900000) + 100000;

        return String.valueOf(numero);
    }
}