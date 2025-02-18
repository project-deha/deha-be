package me.utku.dehabe.service.user.command;

import me.utku.dehabe.dto.user.RegisterRequestDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.generic.Command;
import me.utku.dehabe.mapper.UserMapper;
import me.utku.dehabe.model.User;
import me.utku.dehabe.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserCommand implements Command<UserDto, RegisterRequestDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CreateUserCommand(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto execute(RegisterRequestDto registerRequestDto) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(registerRequestDto.email()))) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        User user = userMapper.toEntity(registerRequestDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }
}
