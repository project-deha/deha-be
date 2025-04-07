package org.deha.be.service.domain.user.command;

import org.deha.be.dto.user.UpdateUserRequestDto;
import org.deha.be.generic.command.Command;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserCommand implements Command<User, UpdateUserRequestDto> {
    private final UserMapper userMapper;

    public UpdateUserCommand(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User execute(UpdateUserRequestDto updateUserRequestDto) {
        return userMapper.partialUpdate(updateUserRequestDto.updateData(), updateUserRequestDto.existingUser());
    }
}
