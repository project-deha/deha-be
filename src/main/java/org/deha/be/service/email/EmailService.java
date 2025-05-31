package org.deha.be.service.email;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.email.SendEarthquakeEmailParameters;
import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.deha.be.dto.user.ResetPasswordEmailParameters;
import org.deha.be.model.Alarm;
import org.deha.be.model.User;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.service.email.command.SendEarthquakeEmailCommand;
import org.deha.be.service.email.command.SendResetPasswordEmailCommand;
import org.deha.be.service.email.command.SendVerificationEmailCommand;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SendVerificationEmailCommand sendVerificationEmailCommand;
    private final SendEarthquakeEmailCommand sendEarthquakeEmailCommand;
    private final SendResetPasswordEmailCommand sendResetPasswordEmailCommand;

    public void sendVerificationEmail(EmailVerificationDto emailVerificationDto) {
        sendVerificationEmailCommand.execute(emailVerificationDto);
    }

    public void sendEarthquakeEmail(Earthquake earthquake, Alarm alarm) {
        SendEarthquakeEmailParameters parameters = new SendEarthquakeEmailParameters(alarm.getOwner(), earthquake);
        sendEarthquakeEmailCommand.execute(parameters);
    }

    public void sendResetPasswordEmail(User user, String newPassword) {
        ResetPasswordEmailParameters parameters = new ResetPasswordEmailParameters(user, newPassword);
        sendResetPasswordEmailCommand.execute(parameters);
    }
}
