package me.utku.dehabe.service.emailverification.action;

import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.generic.Action;
import me.utku.dehabe.mapper.EmailVerificationMapper;
import me.utku.dehabe.mapper.UserMapper;
import me.utku.dehabe.model.EmailVerification;
import me.utku.dehabe.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateEmailVerificationService implements Action<EmailVerificationDto, UserDto> {
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailVerificationMapper emailVerificationMapper;
    private final UserMapper userMapper;

    public CreateEmailVerificationService(EmailVerificationRepository emailVerificationRepository, EmailVerificationMapper emailVerificationMapper, UserMapper userMapper) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailVerificationMapper = emailVerificationMapper;
        this.userMapper = userMapper;
    }

    @Override
    public EmailVerificationDto execute(UserDto userDto) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setUser(userMapper.toEntity(userDto));
        return emailVerificationMapper.toDto(emailVerificationRepository.save(emailVerification));
    }
}