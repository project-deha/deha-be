package me.utku.dehabe.service.email;

import me.utku.dehabe.dto.email.EmailRequestDto;
import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.service.email.action.SendVerificationEmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final SendVerificationEmailService sendVerificationEmailService;

    public EmailService(SendVerificationEmailService sendVerificationEmailService) {
        this.sendVerificationEmailService = sendVerificationEmailService;
    }

    public void sendVerificationEmail(EmailVerificationDto emailVerificationDto) {
        EmailRequestDto verificationEmailRequest = sendVerificationEmailService.prepareVerificationEmailRequest(emailVerificationDto);
        sendVerificationEmailService.execute(verificationEmailRequest);
    }
}
