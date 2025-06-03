package org.deha.be.service.domain.user.command;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.user.ChangePasswordParameters;
import org.deha.be.enums.ExceptionDescription;
import org.deha.be.exception.OperationDeniedException;
import org.deha.be.generic.command.Command;
import org.deha.be.model.User;
import org.deha.be.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordCommand implements Command<User, ChangePasswordParameters> {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User execute(ChangePasswordParameters changePasswordParameters) {
        if (changePasswordParameters.user().getPassword().matches(bCryptPasswordEncoder.encode(changePasswordParameters.newPassword()))) {
            throw new OperationDeniedException(ExceptionDescription.PASSWORDS_DO_NOT_MATCH);
        }
        changePasswordParameters.user().setPassword(bCryptPasswordEncoder.encode(changePasswordParameters.newPassword()));
        return userRepository.save(changePasswordParameters.user());
    }
}
