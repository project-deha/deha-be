package me.utku.dehabe.dto.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public record AuthenticateUserParameters(LoginRequestDto loginRequestDto, HttpServletRequest request,
                                         HttpServletResponse response) {
}
