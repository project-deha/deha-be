package me.utku.dehabe.service.email;

import me.utku.dehabe.dto.email.EmailRequestDto;
import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.service.email.command.SendVerificationEmailCommand;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final SendVerificationEmailCommand sendVerificationEmailCommand;

    public EmailService(SendVerificationEmailCommand sendVerificationEmailCommand) {
        this.sendVerificationEmailCommand = sendVerificationEmailCommand;
    }

    public void sendVerificationEmail(EmailVerificationDto emailVerificationDto) {
        EmailRequestDto verificationEmailRequest = sendVerificationEmailCommand.prepareVerificationEmailRequest(emailVerificationDto);
        sendVerificationEmailCommand.execute(verificationEmailRequest);
    }
}
