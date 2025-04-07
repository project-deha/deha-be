package org.deha.be.service.auth.command;

import org.deha.be.dto.auth.VerifyRequestDto;
import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.exception.EmailVerificationException;
import org.deha.be.generic.Command;
import org.deha.be.model.User;
import org.deha.be.service.domain.emailverification.EmailVerificationQueryService;
import org.deha.be.service.domain.emailverification.EmailVerificationService;
import org.deha.be.service.domain.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class VerifyCommand extends BaseAuthenticationCommand implements Command<UserDto, VerifyRequestDto> {
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;
    private final EmailVerificationQueryService emailVerificationQueryService;

    public VerifyCommand(UserService userService, EmailVerificationService emailVerificationService, EmailVerificationQueryService emailVerificationQueryService) {
        this.userService = userService;
        this.emailVerificationService = emailVerificationService;
        this.emailVerificationQueryService = emailVerificationQueryService;
    }

    @Override
    public UserDto execute(VerifyRequestDto verifyRequestDto) {
        User authenticatedUser = getAuthenticatedUser();
        EmailVerificationDto emailVerification = emailVerificationQueryService.findByUserId(authenticatedUser.getId());
        if (emailVerification.code() != verifyRequestDto.verificationCode()) {
            throw new EmailVerificationException();
        }
        UserDto verifiedUser = userService.verifyUser(authenticatedUser);
        emailVerificationService.deleteEmailVerification(authenticatedUser.getId());
        return verifiedUser;
    }
}
