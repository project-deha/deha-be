package org.deha.be.service.domain.emailverification.command;

import jakarta.persistence.EntityNotFoundException;
import org.deha.be.generic.Command;
import org.deha.be.model.EmailVerification;
import org.deha.be.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteEmailVerificationCommand implements Command<Void, UUID> {
    private final EmailVerificationRepository emailVerificationRepository;

    public DeleteEmailVerificationCommand(EmailVerificationRepository emailVerificationRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Override
    public Void execute(UUID userId) {
        EmailVerification emailVerification = emailVerificationRepository.findByUser_Id(userId).orElseThrow(EntityNotFoundException::new);
        emailVerificationRepository.delete(emailVerification);
        return null;
    }
}
