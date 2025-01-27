package me.utku.dehabe.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.auth.AuthenticateUserParameters;
import me.utku.dehabe.dto.auth.LoginRequestDto;
import me.utku.dehabe.dto.auth.VerifyRequestDto;
import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.dto.user.RegisterRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.generic.GenericResponse;
import me.utku.dehabe.service.auth.command.AuthenticateCommand;
import me.utku.dehabe.service.auth.command.RegisterCommand;
import me.utku.dehabe.service.auth.command.VerifyCommand;
import me.utku.dehabe.service.email.EmailService;
import me.utku.dehabe.service.emailverification.EmailVerificationService;
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
        UserDto userDto = registerCommand.execute(registerRequestDto);
        EmailVerificationDto emailVerificationDto = emailVerificationService.createEmailVerification(userDto);
        emailService.sendVerificationEmail(emailVerificationDto);
        return GenericResponse.ok(userDto);
    }

    public GenericResponse<UserDto> verify(VerifyRequestDto verifyRequestDto) {
        return verifyCommandService.execute(verifyRequestDto);
    }
}
