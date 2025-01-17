package me.utku.dehabe.service.email;

import me.utku.dehabe.dto.email.EmailRequestDto;
import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.service.email.command.SendVerificationServiceEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final SendVerificationServiceEmail sendVerificationEmailService;

    public EmailService(SendVerificationServiceEmail sendVerificationEmailService) {
        this.sendVerificationEmailService = sendVerificationEmailService;
    }

    public void sendVerificationEmail(EmailVerificationDto emailVerificationDto) {
        EmailRequestDto verificationEmailRequest = sendVerificationEmailService.prepareVerificationEmailRequest(emailVerificationDto);
        sendVerificationEmailService.execute(verificationEmailRequest);
    }
}
