package org.deha.be.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.deha.be.dto.auth.AuthenticateUserParameters;
import org.deha.be.dto.auth.LoginRequestDto;
import org.deha.be.dto.auth.VerifyRequestDto;
import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.deha.be.dto.user.RegisterRequestDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.GenericResponse;
import org.deha.be.service.auth.command.AuthenticateCommand;
import org.deha.be.service.auth.command.RegisterCommand;
import org.deha.be.service.auth.command.VerifyCommand;
import org.deha.be.service.domain.emailverification.EmailVerificationService;
import org.deha.be.service.email.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final EmailVerificationService emailVerificationService;
    private final RegisterCommand registerCommand;
    private final AuthenticateCommand authenticateCommand;
    private final VerifyCommand verifyCommandService;
    private final EmailService emailService;

    public GenericResponse<UserDto> authenticate(LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
        return authenticateCommand.execute(new AuthenticateUserParameters(loginRequestDto, request, response));
    }

    public GenericResponse<UserDto> register(RegisterRequestDto registerRequestDto) {
        UserDto registeredUser = registerCommand.execute(registerRequestDto);
        EmailVerificationDto emailVerification = emailVerificationService.createEmailVerification(registeredUser);
        emailService.sendVerificationEmail(emailVerification);
        return GenericResponse.ok(registeredUser);
    }

    public GenericResponse<UserDto> verify(VerifyRequestDto verifyRequestDto) {
        UserDto verifiedUser = verifyCommandService.execute(verifyRequestDto);
        return GenericResponse.ok(verifiedUser);
    }
}
