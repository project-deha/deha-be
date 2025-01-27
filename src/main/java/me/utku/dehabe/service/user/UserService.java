package me.utku.dehabe.service.user;

import me.utku.dehabe.dto.user.RegisterRequestDto;
import me.utku.dehabe.dto.user.UpdateUserRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.model.User;
import me.utku.dehabe.service.user.command.CreateUserCommand;
import me.utku.dehabe.service.user.command.UpdateUserCommand;
import me.utku.dehabe.service.user.command.VerifyUserCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;
    private final VerifyUserCommand verifyUserCommand;

    public UserService(CreateUserCommand createUserCommand, UpdateUserCommand updateUserCommand, VerifyUserCommand verifyUserCommand) {
        this.createUserCommand = createUserCommand;
        this.updateUserCommand = updateUserCommand;
        this.verifyUserCommand = verifyUserCommand;
    }

    public UserDto createUser(RegisterRequestDto registerRequestDto) {
        return createUserCommand.execute(registerRequestDto);
    }

    public UserDto updateUser(UpdateUserRequestDto updateUserRequestDto) {
        return updateUserCommand.execute(updateUserRequestDto);
    }

    public UserDto verifyUser(User user) {
        return verifyUserCommand.execute(user);
    }
}
