package org.deha.be.service.email.command;

import org.deha.be.dto.email.EmailContextBuilder;
import org.deha.be.dto.email.EmailRequestDto;
import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class SendVerificationEmailCommand extends BaseEmailCommand<EmailVerificationDto> {

    protected SendVerificationEmailCommand(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        super(javaMailSender, templateEngine);
    }

    @Override
    public void execute(EmailVerificationDto emailVerificationDto) {
        EmailRequestDto emailRequestDto = prepareVerificationEmailRequest(emailVerificationDto);
        send(emailRequestDto);
    }

    private EmailRequestDto prepareVerificationEmailRequest(EmailVerificationDto emailVerificationDto) {
        Context context = EmailContextBuilder.builder()
                .setVariable("firstName", emailVerificationDto.user().firstName())
                .setVariable("lastName", emailVerificationDto.user().lastName())
                .setVariable("verificationCode", emailVerificationDto.code())
                .build();

        return new EmailRequestDto(
                emailVerificationDto.user().email(),
                "DEHA - Email Verification",
                "email/emailVerification",
                context
        );
    }
}
