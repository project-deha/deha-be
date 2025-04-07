package org.deha.be.service.auth.command;

import org.deha.be.dto.user.RegisterRequestDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.Command;
import org.deha.be.service.domain.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class RegisterCommand implements Command<UserDto, RegisterRequestDto> {
    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto execute(RegisterRequestDto registerRequestDto) {
        return userService.createUser(registerRequestDto);
    }
}
