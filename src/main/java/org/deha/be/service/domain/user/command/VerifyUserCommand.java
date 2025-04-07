package org.deha.be.service.domain.user.command;

import org.deha.be.enums.Role;
import org.deha.be.generic.command.Command;
import org.deha.be.model.User;
import org.deha.be.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class VerifyUserCommand implements Command<User, User> {
    private final UserRepository userRepository;

    public VerifyUserCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(User user) {
        user.setVerified(true);
        user.getAuthorities().clear();
        user.getAuthorities().add(Role.ROLE_RESEARCHER);
        return userRepository.save(user);
    }
}
