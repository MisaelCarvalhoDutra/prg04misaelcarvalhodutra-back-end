package br.com.ifba.prg04pizzly.auth.controller;

import br.com.ifba.prg04pizzly.auth.dto.LoginRequestDTO;
import br.com.ifba.prg04pizzly.auth.dto.LoginResponseDTO;
import br.com.ifba.prg04pizzly.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ifba.prg04pizzly.auth.dto.GoogleLoginRequestDTO;

//Controller responsável pelos endpoints de autenticação.
//recebe as requisições e delega as regras ao AuthService.
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // service responsável pelas regras de autenticação
    private final AuthService authService;

    //realiza login de cliente ou funcionário
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginDTO) {

        // autentica o usuário com e-mail e senha
        LoginResponseDTO usuarioLogado = authService.login(loginDTO);

        return ResponseEntity.ok(usuarioLogado);
    }

    //realiza login utilizando conta Google
    @PostMapping("/google")
    public ResponseEntity<LoginResponseDTO> loginComGoogle(
            @Valid @RequestBody GoogleLoginRequestDTO googleDTO) {

        // autentica o usuário utilizando o token enviado pelo Google
        LoginResponseDTO usuarioLogado =
                authService.loginComGoogle(googleDTO);

        return ResponseEntity.ok(usuarioLogado);
    }
}