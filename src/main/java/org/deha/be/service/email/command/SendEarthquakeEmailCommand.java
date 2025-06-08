package org.deha.be.service.email.command;

import org.deha.be.dto.email.EmailContextBuilder;
import org.deha.be.dto.email.EmailRequestDto;
import org.deha.be.dto.email.SendEarthquakeEmailParameters;
import org.deha.be.model.User;
import org.deha.be.model.earthquakes.Earthquake;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.ZoneOffset;

@Service
public class SendEarthquakeEmailCommand extends BaseEmailCommand<SendEarthquakeEmailParameters> {

    protected SendEarthquakeEmailCommand(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        super(javaMailSender, templateEngine);
    }

    @Override
    public void execute(SendEarthquakeEmailParameters sendEarthquakeEmailParameters) {
        EmailRequestDto emailRequestDto = prepareEarthquakeEmailRequest(
                sendEarthquakeEmailParameters.user(),
                sendEarthquakeEmailParameters.earthquake()
        );
        send(emailRequestDto);
    }

    public EmailRequestDto prepareEarthquakeEmailRequest(User user, Earthquake earthquake) {
        Context context = EmailContextBuilder.builder()
                .setVariable("firstName", user.getFirstName())
                .setVariable("city", earthquake.getLocation().getCity())
                .setVariable("latitude", earthquake.getLocation().getLatitude())
                .setVariable("longitude", earthquake.getLocation().getLongitude())
                .setVariable("magnitude", earthquake.getMagnitude())
                .setVariable("depth", earthquake.getDepth())
                .setVariable("occurrenceDate", earthquake.getOccurrenceDate().atOffset(ZoneOffset.ofHours(3)).toString())
                .build();

        return new EmailRequestDto(
                user.getEmail(),
                "DEHA - Earthquake Alarm",
                "email/earthquakeAlarm",
                context
        );
    }
}
