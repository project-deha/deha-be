package org.deha.be.service.domain.user.command;

import org.deha.be.dto.user.RegisterRequestDto;
import org.deha.be.enums.ExceptionDescription;
import org.deha.be.generic.command.Command;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.deha.be.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserCommand implements Command<User, RegisterRequestDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CreateUserCommand(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User execute(RegisterRequestDto registerRequestDto) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(registerRequestDto.email()))) {
            throw new DataIntegrityViolationException(ExceptionDescription.USER_ALREADY_EXISTS.getValue());
        }
        User user = userMapper.toEntity(registerRequestDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
