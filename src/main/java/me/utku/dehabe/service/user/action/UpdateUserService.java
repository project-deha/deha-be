package me.utku.dehabe.service.user.action;

import me.utku.dehabe.dto.user.UpdateUserRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.generic.Action;
import me.utku.dehabe.mapper.UserMapper;
import me.utku.dehabe.model.User;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService implements Action<UserDto, UpdateUserRequestDto> {
    private final UserMapper userMapper;

    public UpdateUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDto execute(UpdateUserRequestDto updateUserRequestDto) {
        User user = userMapper.partialUpdate(updateUserRequestDto.updateData(), updateUserRequestDto.existingUser());
        return userMapper.toDto(user);
    }
}