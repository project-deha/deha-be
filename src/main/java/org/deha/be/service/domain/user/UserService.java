package org.deha.be.service.domain.user;

import org.deha.be.dto.user.RegisterRequestDto;
import org.deha.be.dto.user.UpdateUserRequestDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.deha.be.service.domain.user.command.CreateUserCommand;
import org.deha.be.service.domain.user.command.UpdateUserCommand;
import org.deha.be.service.domain.user.command.VerifyUserCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserMapper userMapper;
    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;
    private final VerifyUserCommand verifyUserCommand;

    public UserService(UserMapper userMapper, CreateUserCommand createUserCommand, UpdateUserCommand updateUserCommand, VerifyUserCommand verifyUserCommand) {
        this.userMapper = userMapper;
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
        User verifiedUser = verifyUserCommand.execute(user);
        return userMapper.toDto(verifiedUser);
    }
}
