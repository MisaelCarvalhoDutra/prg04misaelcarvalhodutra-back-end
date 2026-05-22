package br.com.ifba.prg04pizzly.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Handler global de exceções
// aqui centraliza o tratamento de exceções lançadas na aplicação
@RestControllerAdvice
public class GlobalExceptionHandler {

    // trata exceções do tipo ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        //e aqui retorna status 404 e a mensagem da exceção
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}