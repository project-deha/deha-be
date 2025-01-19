package me.utku.dehabe.service.email.command;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import me.utku.dehabe.dto.email.EmailRequestDto;
import me.utku.dehabe.exception.EmailSendingException;
import me.utku.dehabe.generic.Command;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;

import java.io.UnsupportedEncodingException;

public abstract class BaseEmailCommandService<R> implements Command<R, EmailRequestDto> {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.from}")
    private String from;

    protected BaseEmailCommandService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    protected void send(EmailRequestDto emailRequestDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom(new InternetAddress(username, from));
            mimeMessageHelper.setTo(emailRequestDto.to());
            mimeMessageHelper.setSubject(emailRequestDto.subject());
            mimeMessageHelper.setText(templateEngine.process(emailRequestDto.templateName(), emailRequestDto.context()), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailSendingException(e.getCause());
        }
    }
}
