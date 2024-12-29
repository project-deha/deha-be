package me.utku.dehabe.service.emailverification;

import jakarta.persistence.EntityNotFoundException;
import me.utku.dehabe.dto.emailverification.EmailVerificationDto;
import me.utku.dehabe.mapper.EmailVerificationMapper;
import me.utku.dehabe.repository.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class EmailVerificationQueryService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailVerificationMapper emailVerificationMapper;

    public EmailVerificationQueryService(EmailVerificationRepository emailVerificationRepository, EmailVerificationMapper emailVerificationMapper) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailVerificationMapper = emailVerificationMapper;
    }

    public EmailVerificationDto findByUserId(UUID userId) {
        return emailVerificationMapper.toDto(emailVerificationRepository
                .findByUser_IdAndExpireAtGreaterThanEqual(userId, Instant.now())
                .orElseThrow(EntityNotFoundException::new));
    }
}
