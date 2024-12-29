package me.utku.dehabe.service.email.action;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import me.utku.dehabe.dto.email.EmailRequestDto;
import me.utku.dehabe.exception.EmailSendingException;
import me.utku.dehabe.generic.Action;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;

public abstract class EmailBaseActionService<R> implements Action<R, EmailRequestDto> {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    protected EmailBaseActionService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    protected void send(EmailRequestDto emailRequestDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setTo(emailRequestDto.to());
            mimeMessageHelper.setSubject(emailRequestDto.subject());
            mimeMessageHelper.setText(templateEngine.process(emailRequestDto.templateName(), emailRequestDto.context()), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailSendingException(e.getCause());
        }
    }
}
