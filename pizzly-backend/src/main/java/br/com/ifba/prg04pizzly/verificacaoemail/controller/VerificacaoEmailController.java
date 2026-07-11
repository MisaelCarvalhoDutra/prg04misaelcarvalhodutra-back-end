package br.com.ifba.prg04pizzly.verificacaoemail.controller;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.EnviarCodigoDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.RedefinirSenhaDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.ValidarCadastroDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.service.VerificacaoEmailIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller responsável pelos endpoints de verificação por e-mail
@RestController
@RequestMapping("/verificacao-email")
@RequiredArgsConstructor
public class VerificacaoEmailController
        implements VerificacaoEmailIController {

    // Service responsável pelo envio e validação por e-mail.
    private final VerificacaoEmailIService verificacaoEmailService;

    // envia código para validar o e-mail antes do cadastro
    @Override
    @PostMapping("/cadastro/enviar")
    public ResponseEntity<Void> enviarCodigoCadastro(
            @Valid @RequestBody EnviarCodigoDTO dto) {

        verificacaoEmailService.enviarCodigoCadastro(
                dto.getEmail()
        );

        return ResponseEntity.ok().build();
    }

    // aqui valida o código recebido e cria a conta do cliente.
    @Override
    @PostMapping("/cadastro/validar")
    public ResponseEntity<ClienteResponseDTO> validarCadastro(
            @Valid @RequestBody ValidarCadastroDTO dto) {

        ClienteResponseDTO clienteCriado =
                verificacaoEmailService.validarCadastro(
                        dto.getEmail(),
                        dto.getCodigo(),
                        dto.getCliente()
                );

        return ResponseEntity.status(201).body(clienteCriado);
    }

    // Envia um link temporário para recuperação de senha
    @Override
    @PostMapping("/senha/enviar")
    public ResponseEntity<Void> enviarLinkRecuperacaoSenha(
            @Valid @RequestBody EnviarCodigoDTO dto) {

        verificacaoEmailService.enviarLinkRecuperacaoSenha(
                dto.getEmail()
        );

        return ResponseEntity.ok().build();
    }

    // valida o token e redefine a senha do usuário
    @Override
    @PostMapping("/senha/redefinir")
    public ResponseEntity<Void> redefinirSenha(
            @Valid @RequestBody RedefinirSenhaDTO dto) {

        verificacaoEmailService.redefinirSenha(
                dto.getToken(),
                dto.getNovaSenha()
        );

        return ResponseEntity.noContent().build();
    }
}