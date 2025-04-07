package org.deha.be.service.email.command;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.deha.be.dto.email.EmailRequestDto;
import org.deha.be.exception.EmailSendingException;
import org.deha.be.generic.command.EffectCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;

import java.io.UnsupportedEncodingException;

public abstract class BaseEmailCommand<P> implements EffectCommand<P> {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.from}")
    private String from;

    protected BaseEmailCommand(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    protected void send(EmailRequestDto emailRequestDto) {
        try {
            MimeMessage mimeMessage = prepareMimeMessage(emailRequestDto);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailSendingException();
        }
    }

    private MimeMessage prepareMimeMessage(EmailRequestDto emailRequestDto) throws UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        mimeMessageHelper.setFrom(new InternetAddress(username, from));
        mimeMessageHelper.setTo(emailRequestDto.to());
        mimeMessageHelper.setSubject(emailRequestDto.subject());
        mimeMessageHelper.setText(templateEngine.process(emailRequestDto.templateName(), emailRequestDto.context()), true);
        return mimeMessage;
    }
}
