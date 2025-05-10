package org.deha.be.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.deha.be.dto.auth.AuthenticateUserParameters;
import org.deha.be.dto.auth.LoginRequestDto;
import org.deha.be.dto.auth.VerifyRequestDto;
import org.deha.be.dto.user.RegisterRequestParameters;
import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.GenericResponse;
import org.deha.be.service.auth.command.AuthenticateCommand;
import org.deha.be.service.auth.command.RegisterCommand;
import org.deha.be.service.auth.command.VerifyCommand;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RegisterCommand registerCommand;
    private final AuthenticateCommand authenticateCommand;
    private final VerifyCommand verifyCommandService;

    public GenericResponse<UserDto> authenticate(LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
        UserDto authenticatedUser = authenticateCommand.execute(new AuthenticateUserParameters(loginRequestDto, request, response));
        return GenericResponse.ok(authenticatedUser);
    }

    public GenericResponse<UserDto> register(RegisterRequestParameters registerRequestParameters) {
        UserDto registeredUser = registerCommand.execute(registerRequestParameters);
        return GenericResponse.ok(registeredUser);
    }

    public GenericResponse<UserDto> verify(VerifyRequestDto verifyRequestDto) {
        UserDto verifiedUser = verifyCommandService.execute(verifyRequestDto);
        return GenericResponse.ok(verifiedUser);
    }
}
