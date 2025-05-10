package org.deha.be.service.auth.command;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.deha.be.dto.user.RegisterRequestParameters;
import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.command.Command;
import org.deha.be.service.domain.emailverification.EmailVerificationService;
import org.deha.be.service.domain.user.UserService;
import org.deha.be.service.email.EmailService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCommand implements Command<UserDto, RegisterRequestParameters> {
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;

    @Override
    public UserDto execute(RegisterRequestParameters registerRequestParameters) {
        UserDto registeredUser = userService.createUser(registerRequestParameters);
        EmailVerificationDto emailVerification = emailVerificationService.createEmailVerification(registeredUser);
        emailService.sendVerificationEmail(emailVerification);
        return registeredUser;
    }
}
