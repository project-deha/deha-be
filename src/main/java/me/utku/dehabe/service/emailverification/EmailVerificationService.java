package me.utku.dehabe.service.emailverification;

import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.service.emailverification.command.CreateEmailVerificationCommand;
import me.utku.dehabe.service.emailverification.command.DeleteEmailVerificationCommand;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailVerificationService {
    private final CreateEmailVerificationCommand createEmailVerificationCommand;
    private final DeleteEmailVerificationCommand deleteEmailVerificationCommand;

    public EmailVerificationService(CreateEmailVerificationCommand createEmailVerificationCommand, DeleteEmailVerificationCommand deleteEmailVerificationCommand) {
        this.createEmailVerificationCommand = createEmailVerificationCommand;
        this.deleteEmailVerificationCommand = deleteEmailVerificationCommand;
    }

    public EmailVerificationDto createEmailVerification(UserDto userDto) {
        return createEmailVerificationCommand.execute(userDto);
    }

    public void deleteEmailVerification(UUID userId) {
        deleteEmailVerificationCommand.execute(userId);
    }
}
