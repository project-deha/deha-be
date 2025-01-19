package me.utku.dehabe.service.auth.command;

import me.utku.dehabe.dto.auth.VerifyRequestDto;
import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.exception.EmailVerificationException;
import me.utku.dehabe.generic.Command;
import me.utku.dehabe.generic.GenericResponse;
import me.utku.dehabe.model.User;
import me.utku.dehabe.service.emailverification.EmailVerificationQueryService;
import me.utku.dehabe.service.emailverification.EmailVerificationService;
import me.utku.dehabe.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class VerifyService extends BaseAuthCommandService implements Command<GenericResponse<UserDto>, VerifyRequestDto> {
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;
    private final EmailVerificationQueryService emailVerificationQueryService;

    public VerifyService(UserService userService, EmailVerificationService emailVerificationService, EmailVerificationQueryService emailVerificationQueryService) {
        this.userService = userService;
        this.emailVerificationService = emailVerificationService;
        this.emailVerificationQueryService = emailVerificationQueryService;
    }

    @Override
    public GenericResponse<UserDto> execute(VerifyRequestDto verifyRequestDto) {
        User authenticatedUser = getAuthenticatedUser();
        EmailVerificationDto emailVerification = emailVerificationQueryService.findByUserId(authenticatedUser.getId());
        if (emailVerification.code() == verifyRequestDto.verificationCode()) {
            GenericResponse<UserDto> response = GenericResponse.ok(userService.verifyUser(authenticatedUser));
            emailVerificationService.deleteEmailVerification(authenticatedUser.getId());
            return response;
        } else {
            throw new EmailVerificationException();
        }
    }
}
