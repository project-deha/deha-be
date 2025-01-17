package me.utku.dehabe.service.user;

import me.utku.dehabe.dto.user.RegisterRequestDto;
import me.utku.dehabe.dto.user.UpdateUserRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.model.User;
import me.utku.dehabe.service.user.command.CreateUserService;
import me.utku.dehabe.service.user.command.UpdateUserService;
import me.utku.dehabe.service.user.command.VerifyUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final CreateUserService createUserService;
    private final UpdateUserService updateUserService;
    private final VerifyUserService verifyUserService;

    public UserService(CreateUserService createUserService, UpdateUserService updateUserService, VerifyUserService verifyUserService) {
        this.createUserService = createUserService;
        this.updateUserService = updateUserService;
        this.verifyUserService = verifyUserService;
    }

    public UserDto createUser(RegisterRequestDto registerRequestDto) {
        return createUserService.execute(registerRequestDto);
    }

    public UserDto updateUser(UpdateUserRequestDto updateUserRequestDto) {
        return updateUserService.execute(updateUserRequestDto);
    }

    public UserDto verifyUser(User user) {
        return verifyUserService.execute(user);
    }
}
