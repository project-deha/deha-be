package org.deha.be.service.domain.emailverification.command;

import org.deha.be.dto.user.UserDto;
import org.deha.be.generic.Command;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.EmailVerification;
import org.deha.be.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateEmailVerificationCommand implements Command<EmailVerification, UserDto> {
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserMapper userMapper;

    public CreateEmailVerificationCommand(EmailVerificationRepository emailVerificationRepository, UserMapper userMapper) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.userMapper = userMapper;
    }

    @Override
    public EmailVerification execute(UserDto userDto) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setUser(userMapper.toEntity(userDto));
        return emailVerificationRepository.save(emailVerification);
    }
}
