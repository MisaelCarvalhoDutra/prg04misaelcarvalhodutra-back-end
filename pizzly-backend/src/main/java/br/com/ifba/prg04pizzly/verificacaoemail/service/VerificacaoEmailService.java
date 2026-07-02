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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

//Service responsável pelo envio e validação
//dos códigos de verificação enviados por e-mail
@Service
@RequiredArgsConstructor
public class VerificacaoEmailService implements VerificacaoEmailIService {

    private static final int TEMPO_EXPIRACAO_MINUTOS = 1;//ou seja, 1 minuto

    // gerador mais seguro para códigos temporários
    private static final SecureRandom RANDOM = new SecureRandom();

    // responsável por enviar e-mails
    private final JavaMailSender mailSender;

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

    //envia código para recuperação de senha
    @Override
    @Transactional
    public void enviarCodigoRecuperacaoSenha(String email) {

        // verifica se existe usuário cadastrado com o e-mail informado
        usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BusinessException("E-mail não encontrado")); //exceção caso n encontre

        enviarCodigo(
                email,
                TipoVerificacaoEmail.RECUPERACAO_SENHA,
                "Código para redefinir senha - Pizzly"
        );
    }

    //redefine a senha do usuário após validar o código
    @Override
    @Transactional
    public void redefinirSenha(
            String email,
            String codigo,
            String novaSenha
    ) {
        validarCodigo(
                email,
                codigo,
                TipoVerificacaoEmail.RECUPERACAO_SENHA
        );

        // busca o usuário para atualizar a senha
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BusinessException("E-mail não encontrado"));

        // atualiza a senha do usuário
        usuario.setSenha(novaSenha);

        usuarioRepository.save(usuario);
    }

    //gera um código aleatório, salva no banco e realiza o envio do e-mail
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
                LocalDateTime.now().plusMinutes(TEMPO_EXPIRACAO_MINUTOS)
        );
        codigoVerificacao.setUsado(false);

        codigoRepository.save(codigoVerificacao);

        // monta a mensagem de e-mail com o código de verificação
        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setTo(email);
        mensagem.setSubject(assunto);
        mensagem.setText(
                "Olá!\n\n" +
                        "Seu código de verificação é:\n\n" +
                        codigo +
                        "\n\nEste código expira em " +
                        TEMPO_EXPIRACAO_MINUTOS +
                        " minuto.\n\n" +
                        "Equipe Pizzly"
        );

        // envia o e-mail para o usuário
        mailSender.send(mensagem);
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