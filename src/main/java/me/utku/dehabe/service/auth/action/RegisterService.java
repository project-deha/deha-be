package me.utku.dehabe.service.auth.action;

import me.utku.dehabe.dto.user.RegisterRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.generic.Action;
import me.utku.dehabe.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements Action<UserDto, RegisterRequestDto> {
    private final UserService userService;

    public RegisterService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto execute(RegisterRequestDto registerRequestDto) {
        return userService.createUser(registerRequestDto);
    }
}
