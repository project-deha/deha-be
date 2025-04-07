package org.deha.be.service.domain.emailverification;

import jakarta.persistence.EntityNotFoundException;
import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.deha.be.enums.ExceptionDescription;
import org.deha.be.mapper.EmailVerificationMapper;
import org.deha.be.repository.EmailVerificationRepository;
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
                .orElseThrow(() -> new EntityNotFoundException(ExceptionDescription.ENTITY_NOT_FOUND.getValue())));
    }
}
