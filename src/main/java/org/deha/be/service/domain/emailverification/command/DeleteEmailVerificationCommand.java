package org.deha.be.service.domain.emailverification.command;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.EmailVerification;
import org.deha.be.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteEmailVerificationCommand implements EffectCommand<UUID> {
    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    public void execute(UUID userId) {
        EmailVerification emailVerification = emailVerificationRepository.findByUser_Id(userId).orElseThrow(EntityNotFoundException::new);
        emailVerificationRepository.delete(emailVerification);
    }
}
