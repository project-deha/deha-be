package org.deha.be.service.domain.user.command;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.user.UpdateUserRequestParameters;
import org.deha.be.generic.command.Command;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.deha.be.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserCommand implements Command<User, UpdateUserRequestParameters> {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public User execute(UpdateUserRequestParameters updateUserRequestParameters) {
        User updatedUser = userMapper.partialUpdate(updateUserRequestParameters.updateData(), updateUserRequestParameters.existingUser());
        return userRepository.save(updatedUser);
    }
}
