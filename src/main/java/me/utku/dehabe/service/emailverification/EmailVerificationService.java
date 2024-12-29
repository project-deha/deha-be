package me.utku.dehabe.service.emailverification;

import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.service.emailverification.action.CreateEmailVerificationService;
import me.utku.dehabe.service.emailverification.action.DeleteEmailVerificationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailVerificationService {
    private final CreateEmailVerificationService createEmailVerificationService;
    private final DeleteEmailVerificationService deleteEmailVerificationService;

    public EmailVerificationService(CreateEmailVerificationService createEmailVerificationService, DeleteEmailVerificationService deleteEmailVerificationService) {
        this.createEmailVerificationService = createEmailVerificationService;
        this.deleteEmailVerificationService = deleteEmailVerificationService;
    }

    public EmailVerificationDto createEmailVerification(UserDto userDto) {
        return createEmailVerificationService.execute(userDto);
    }

    public void deleteEmailVerification(UUID userId) {
        deleteEmailVerificationService.execute(userId);
    }
}
