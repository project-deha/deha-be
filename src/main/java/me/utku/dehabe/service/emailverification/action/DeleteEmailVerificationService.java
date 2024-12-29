package me.utku.dehabe.service.emailverification.action;

import jakarta.persistence.EntityNotFoundException;
import me.utku.dehabe.generic.Action;
import me.utku.dehabe.model.EmailVerification;
import me.utku.dehabe.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteEmailVerificationService implements Action<Void, UUID> {
    private final EmailVerificationRepository emailVerificationRepository;


    public DeleteEmailVerificationService(EmailVerificationRepository emailVerificationRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
    }


    @Override
    public Void execute(UUID userId) {
        EmailVerification emailVerification = emailVerificationRepository.findByUser_Id(userId).orElseThrow(EntityNotFoundException::new);
        emailVerificationRepository.delete(emailVerification);
        return null;
    }
}
