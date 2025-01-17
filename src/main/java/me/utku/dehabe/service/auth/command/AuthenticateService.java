package me.utku.dehabe.service.auth.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.utku.dehabe.dto.auth.AuthenticateUserParameters;
import me.utku.dehabe.dto.auth.LoginRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.exception.ExceptionDescription;
import me.utku.dehabe.generic.Command;
import me.utku.dehabe.generic.GenericResponse;
import me.utku.dehabe.mapper.UserMapper;
import me.utku.dehabe.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService implements Command<GenericResponse<UserDto>, AuthenticateUserParameters> {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final UserMapper userMapper;

    public AuthenticateService(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.userMapper = userMapper;
    }

    @Override
    public GenericResponse<UserDto> execute(AuthenticateUserParameters authenticateUserParameters) {
        try {
            UsernamePasswordAuthenticationToken authToken = generateUnauthenticatedToken(authenticateUserParameters.loginRequestDto());
            Authentication authentication = authenticationManager.authenticate(authToken);
            UserDto userDto = saveAuthenticationIfSuccessful(authentication, authenticateUserParameters.request(), authenticateUserParameters.response());
            return GenericResponse.ok(userDto);
        } catch (Exception e) {
            throw new BadCredentialsException(ExceptionDescription.BAD_CREDENTIALS.getValue());
        }
    }

    private UsernamePasswordAuthenticationToken generateUnauthenticatedToken(LoginRequestDto loginRequestDto) {
        return UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDto.email(), loginRequestDto.password());
    }

    private UserDto saveAuthenticationIfSuccessful(Authentication authentication, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (authentication.isAuthenticated()) {
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, httpServletRequest, httpServletResponse);
            return userMapper.toDto(((User) authentication.getPrincipal()));
        } else {
            return null;
        }
    }
}
