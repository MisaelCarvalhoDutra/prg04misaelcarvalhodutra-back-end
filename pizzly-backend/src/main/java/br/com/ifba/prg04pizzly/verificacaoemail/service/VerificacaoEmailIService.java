package br.com.ifba.prg04pizzly.verificacaoemail.service;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteRequestDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;

// Interface responsável pelos serviços de verificação por e-mail.
public interface VerificacaoEmailIService {

    // Envia código para validar o e-mail no cadastro.
    void enviarCodigoCadastro(String email);

    // Valida o código e cria a conta do cliente.
    ClienteResponseDTO validarCadastro(
            String email,
            String codigo,
            ClienteRequestDTO clienteDTO
    );

    // Envia um link com token para recuperação de senha.
    void enviarLinkRecuperacaoSenha(String email);

    // Valida o token e redefine a senha do usuário.
    void redefinirSenha(
            String token,
            String novaSenha
    );
}