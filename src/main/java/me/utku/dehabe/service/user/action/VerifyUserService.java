package me.utku.dehabe.service.user.action;

import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.enums.Role;
import me.utku.dehabe.generic.Action;
import me.utku.dehabe.mapper.UserMapper;
import me.utku.dehabe.model.User;
import me.utku.dehabe.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class VerifyUserService implements Action<UserDto, User> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public VerifyUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto execute(User user) {
        user.setVerified(true);
        user.getAuthorities().clear();
        user.getAuthorities().add(Role.ROLE_RESEARCHER);
        return userMapper.toDto(userRepository.save(user));
    }
}