package org.deha.be.service.auth.command;

import org.deha.be.dto.auth.AuthenticateUserParameters;
import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.command.Command;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCommand implements Command<UserDto, AuthenticateUserParameters> {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final UserMapper userMapper;

    public AuthenticateCommand(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto execute(AuthenticateUserParameters authenticateUserParameters) {
        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(
                authenticateUserParameters.loginRequestDto().email(),
                authenticateUserParameters.loginRequestDto().password()
        );
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, authenticateUserParameters.request(), authenticateUserParameters.response());
        return userMapper.toDto(((User) authentication.getPrincipal()));
    }
}
