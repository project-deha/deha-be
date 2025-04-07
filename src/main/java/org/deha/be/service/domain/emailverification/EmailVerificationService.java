package org.deha.be.service.domain.emailverification;

import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.mapper.EmailVerificationMapper;
import org.deha.be.model.EmailVerification;
import org.deha.be.service.domain.emailverification.command.CreateEmailVerificationCommand;
import org.deha.be.service.domain.emailverification.command.DeleteEmailVerificationCommand;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailVerificationService {
    private final EmailVerificationMapper emailVerificationMapper;
    private final CreateEmailVerificationCommand createEmailVerificationCommand;
    private final DeleteEmailVerificationCommand deleteEmailVerificationCommand;

    public EmailVerificationService(EmailVerificationMapper emailVerificationMapper, CreateEmailVerificationCommand createEmailVerificationCommand, DeleteEmailVerificationCommand deleteEmailVerificationCommand) {
        this.emailVerificationMapper = emailVerificationMapper;
        this.createEmailVerificationCommand = createEmailVerificationCommand;
        this.deleteEmailVerificationCommand = deleteEmailVerificationCommand;
    }

    public EmailVerificationDto createEmailVerification(UserDto userDto) {
        EmailVerification emailVerification = createEmailVerificationCommand.execute(userDto);
        return emailVerificationMapper.toDto(emailVerification);
    }

    public void deleteEmailVerification(UUID userId) {
        deleteEmailVerificationCommand.execute(userId);
    }
}
