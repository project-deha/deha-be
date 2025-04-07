package org.deha.be.service.email;

import org.deha.be.dto.email.EmailRequestDto;
import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.deha.be.service.email.command.SendVerificationEmailCommand;
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
