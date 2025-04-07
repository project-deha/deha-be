package org.deha.be.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.deha.be.dto.auth.LoginRequestDto;
import org.deha.be.dto.auth.VerifyRequestDto;
import org.deha.be.dto.user.RegisterRequestDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.GenericResponse;
import org.deha.be.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<UserDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        GenericResponse<UserDto> authResponse = authService.authenticate(loginRequestDto, httpServletRequest, httpServletResponse);
        return authResponse.toResponseEntity();
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<UserDto>> register(@RequestBody RegisterRequestDto registerRequestDto) {
        GenericResponse<UserDto> authResponse = authService.register(registerRequestDto);
        return authResponse.toResponseEntity();
    }

    @PostMapping("/verify")
    public ResponseEntity<GenericResponse<UserDto>> verify(@RequestBody VerifyRequestDto verifyRequestDto) {
        GenericResponse<UserDto> authResponse = authService.verify(verifyRequestDto);
        return authResponse.toResponseEntity();
    }
}