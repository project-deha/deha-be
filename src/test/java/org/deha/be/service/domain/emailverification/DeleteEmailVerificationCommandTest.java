package org.deha.be.service.domain.emailverification;

import jakarta.persistence.EntityNotFoundException;
import org.deha.be.model.EmailVerification;
import org.deha.be.model.User;
import org.deha.be.repository.EmailVerificationRepository;
import org.deha.be.service.domain.emailverification.command.DeleteEmailVerificationCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DeleteEmailVerificationCommandTest {
    @Mock
    EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    DeleteEmailVerificationCommand deleteEmailVerificationCommand;

    EmailVerification emailVerification;

    @BeforeEach
    public void setUp() {
        emailVerification = new EmailVerification();
        emailVerification.prePersist();
    }

    @Test
    public void deleteEmailVerificationIfExist() {
        UUID uuid = UUID.randomUUID();

        User user = new User();
        user.setId(uuid);
        emailVerification.setUser(user);

        Mockito.when(emailVerificationRepository.findByUser_Id(uuid)).thenReturn(Optional.ofNullable(emailVerification));

        Assertions.assertDoesNotThrow(() -> deleteEmailVerificationCommand.execute(uuid));
    }

    @Test
    public void throwErrorIfDoesNotExist() {
        Mockito.when(emailVerificationRepository.findByUser_Id(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> deleteEmailVerificationCommand.execute(UUID.randomUUID())
        );
    }
}
