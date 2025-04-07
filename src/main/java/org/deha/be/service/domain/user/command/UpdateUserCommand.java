package org.deha.be.service.domain.user.command;

import org.deha.be.dto.user.UpdateUserRequestDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.Command;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserCommand implements Command<UserDto, UpdateUserRequestDto> {
    private final UserMapper userMapper;

    public UpdateUserCommand(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDto execute(UpdateUserRequestDto updateUserRequestDto) {
        User user = userMapper.partialUpdate(updateUserRequestDto.updateData(), updateUserRequestDto.existingUser());
        return userMapper.toDto(user);
    }
}
