package me.utku.dehabe.service.auth.command;

import me.utku.dehabe.dto.user.RegisterRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.generic.Command;
import me.utku.dehabe.service.user.UserService;
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
