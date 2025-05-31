package org.deha.be.service.email.command;

import org.deha.be.dto.email.EmailContextBuilder;
import org.deha.be.dto.email.EmailRequestDto;
import org.deha.be.dto.user.ResetPasswordEmailParameters;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class SendResetPasswordEmailCommand extends BaseEmailCommand<ResetPasswordEmailParameters> {
    protected SendResetPasswordEmailCommand(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        super(javaMailSender, templateEngine);
    }

    @Override
    public void execute(ResetPasswordEmailParameters parameters) {
        EmailRequestDto emailRequestDto = prepareVerificationEmailRequest(parameters);
        send(emailRequestDto);
    }

    private EmailRequestDto prepareVerificationEmailRequest(ResetPasswordEmailParameters parameters) {
        Context context = EmailContextBuilder.builder()
                .setVariable("firstName", parameters.user().getFirstName())
                .setVariable("lastName", parameters.user().getLastName())
                .setVariable("resetPassword", parameters.newPassword())
                .build();

        return new EmailRequestDto(
                parameters.user().getEmail(),
                "DEHA - Reset Password",
                "email/resetPassword",
                context
        );
    }
}
