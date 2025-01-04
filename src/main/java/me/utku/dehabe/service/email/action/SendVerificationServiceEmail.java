package me.utku.dehabe.service.email.action;

import me.utku.dehabe.dto.email.EmailContextBuilder;
import me.utku.dehabe.dto.email.EmailRequestDto;
import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class SendVerificationServiceEmail extends BaseEmailActionService<Void> {

    protected SendVerificationServiceEmail(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        super(javaMailSender, templateEngine);
    }

    @Override
    public Void execute(EmailRequestDto emailRequestDto) {
        send(emailRequestDto);
        return null;
    }

    public EmailRequestDto prepareVerificationEmailRequest(EmailVerificationDto emailVerificationDto) {
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
