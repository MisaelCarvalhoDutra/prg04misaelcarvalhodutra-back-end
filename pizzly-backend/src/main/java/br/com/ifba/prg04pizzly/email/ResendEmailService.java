package br.com.ifba.prg04pizzly.email;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResendEmailService {

    private final Resend resend;

    public ResendEmailService(
            @Value("${RESEND_API_KEY}") String apiKey
    ) {
        this.resend = new Resend(apiKey);
    }

    public void enviarEmail(
            String destinatario,
            String assunto,
            String conteudo
    ) {
        String conteudoHtml = conteudo.replace("\n", "<br>");

        CreateEmailOptions email = CreateEmailOptions.builder()
                .from("Pizzly <onboarding@resend.dev>")
                .to(destinatario)
                .subject(assunto)
                .html(conteudoHtml)
                .build();

        try {
            CreateEmailResponse resposta = resend.emails().send(email);

            System.out.println(
                    "E-mail enviado pelo Resend. ID: " + resposta.getId()
            );
        } catch (ResendException e) {
            throw new RuntimeException(
                    "Erro ao enviar e-mail pelo Resend: " + e.getMessage(),
                    e
            );
        }
    }
}