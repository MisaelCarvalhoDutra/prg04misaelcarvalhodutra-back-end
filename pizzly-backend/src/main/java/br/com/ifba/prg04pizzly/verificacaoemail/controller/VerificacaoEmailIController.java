package br.com.ifba.prg04pizzly.verificacaoemail.controller;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.EnviarCodigoDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.RedefinirSenhaDTO;
import br.com.ifba.prg04pizzly.verificacaoemail.dto.ValidarCadastroDTO;
import org.springframework.http.ResponseEntity;

//interface dos endpoints de verificação por e-mail
public interface VerificacaoEmailIController {

    // envia código para validar o e-mail antes do cadastro
    ResponseEntity<Void> enviarCodigoCadastro(
            EnviarCodigoDTO dto
    );

    // valida o código enviado e cria a conta do cliente
    ResponseEntity<ClienteResponseDTO> validarCadastro(
            ValidarCadastroDTO dto
    );

    // Envia link para recuperação de senha.
    ResponseEntity<Void> enviarLinkRecuperacaoSenha(
            EnviarCodigoDTO dto
    );

    // valida o código enviado e redefine a senha do usuário
    ResponseEntity<Void> redefinirSenha(
            RedefinirSenhaDTO dto
    );
}