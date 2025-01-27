package me.utku.dehabe.service.user.command;

import me.utku.dehabe.dto.user.UpdateUserRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.generic.Command;
import me.utku.dehabe.mapper.UserMapper;
import me.utku.dehabe.model.User;
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
