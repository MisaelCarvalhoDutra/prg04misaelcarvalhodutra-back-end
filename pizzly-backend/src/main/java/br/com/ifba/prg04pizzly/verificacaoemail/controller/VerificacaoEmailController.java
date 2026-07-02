package br.com.ifba.prg04pizzly.verificacaoemail.controller;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.EnviarCodigoDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.RedefinirSenhaDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.ValidarCadastroDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.service.VerificacaoEmailIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

//controller responsável pelos endpoints de verificação por e-mail
@RestController
@RequestMapping("/verificacao-email")
@RequiredArgsConstructor
public class VerificacaoEmailController implements VerificacaoEmailIController {

    // service responsável pelo envio e validação dos códigos por e-mail
    private final VerificacaoEmailIService verificacaoEmailService;

    //envia código para validar o e-mail antes do cadastro
    @Override
    @PostMapping("/cadastro/enviar")
    public ResponseEntity<Void> enviarCodigoCadastro(
            @Valid @RequestBody EnviarCodigoDTO dto) {

        // envia o código de verificação para cadastro
        verificacaoEmailService.enviarCodigoCadastro(dto.getEmail());

        return ResponseEntity.ok().build();
    }

    //valida o código recebido e cria a conta do cliente
    @Override
    @PostMapping("/cadastro/validar")
    public ResponseEntity<ClienteResponseDTO> validarCadastro(
            @Valid @RequestBody ValidarCadastroDTO dto) {

        // valida o código e cria a conta do cliente
        ClienteResponseDTO clienteCriado =
                verificacaoEmailService.validarCadastro(
                        dto.getEmail(),
                        dto.getCodigo(),
                        dto.getCliente()
                );

        return ResponseEntity.status(201).body(clienteCriado);
    }

    //envia código para recuperação de senha
    @Override
    @PostMapping("/senha/enviar")
    public ResponseEntity<Void> enviarCodigoRecuperacaoSenha(
            @Valid @RequestBody EnviarCodigoDTO dto) {

        // envia o código para recuperação de senha
        verificacaoEmailService.enviarCodigoRecuperacaoSenha(dto.getEmail());

        return ResponseEntity.ok().build();
    }

    //valida o código e redefine a senha do usuário
    @Override
    @PostMapping("/senha/redefinir")
    public ResponseEntity<Void> redefinirSenha(
            @Valid @RequestBody RedefinirSenhaDTO dto) {

        // valida o código e atualiza a senha do usuário
        verificacaoEmailService.redefinirSenha(
                dto.getEmail(),
                dto.getCodigo(),
                dto.getNovaSenha()
        );

        return ResponseEntity.noContent().build();
    }
}