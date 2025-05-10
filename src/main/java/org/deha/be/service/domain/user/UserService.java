package org.deha.be.service.domain.user;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.user.ChangePasswordParameters;
import org.deha.be.dto.user.RegisterRequestParameters;
import org.deha.be.dto.user.UpdateUserRequestParameters;
import org.deha.be.dto.user.UserDto;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.deha.be.service.domain.user.command.ChangePasswordCommand;
import org.deha.be.service.domain.user.command.CreateUserCommand;
import org.deha.be.service.domain.user.command.UpdateUserCommand;
import org.deha.be.service.domain.user.command.VerifyUserCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;
    private final VerifyUserCommand verifyUserCommand;
    private final ChangePasswordCommand changePasswordCommand;

    public UserDto createUser(RegisterRequestParameters registerRequestParameters) {
        User createdUser = createUserCommand.execute(registerRequestParameters);
        return userMapper.toDto(createdUser);
    }

    public UserDto updateUser(UpdateUserRequestParameters updateUserRequestParameters) {
        User updatedUser = updateUserCommand.execute(updateUserRequestParameters);
        return userMapper.toDto(updatedUser);
    }

    public UserDto verifyUser(User user) {
        User verifiedUser = verifyUserCommand.execute(user);
        return userMapper.toDto(verifiedUser);
    }

    public UserDto changePassword(ChangePasswordParameters parameters) {
        User changedUser = changePasswordCommand.execute(parameters);
        return userMapper.toDto(changedUser);
    }
}
