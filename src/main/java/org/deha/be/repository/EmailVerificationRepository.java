package org.deha.be.repository;

import org.deha.be.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, UUID> {
    Optional<EmailVerification> findByUser_IdAndExpireAtGreaterThanEqual(UUID userId, Instant expireAt);

    Optional<EmailVerification> findByUser_Id(UUID userId);
}