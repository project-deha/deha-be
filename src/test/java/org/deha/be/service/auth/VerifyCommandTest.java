package org.deha.be.service.auth;

import org.deha.be.dto.auth.VerifyRequestDto;
import org.deha.be.dto.emailverification.EmailVerificationDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.exception.EmailVerificationException;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.deha.be.service.auth.command.VerifyCommand;
import org.deha.be.service.domain.emailverification.EmailVerificationQueryService;
import org.deha.be.service.domain.emailverification.EmailVerificationService;
import org.deha.be.service.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyCommandTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailVerificationService emailVerificationService;

    @Mock
    private EmailVerificationQueryService emailVerificationQueryService;

    @InjectMocks
    private VerifyCommand verifyCommand;

    private User testUser;
    private VerifyRequestDto verifyRequestDto;
    private EmailVerificationDto emailVerificationDto;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setEmail("test@example.com");

        verifyRequestDto = new VerifyRequestDto(123456);

        emailVerificationDto = new EmailVerificationDto(userMapper.toDto(testUser), 123456);
    }

    @Test
    void execute_shouldVerifyUserWhenCodeMatches() {
        // Arrange
        UserDto verifiedUserDto = new UserDto(UUID.randomUUID(), "test@example.com", "Test", "User", null, true);

        // getAuthenticatedUser() çağrısını taklit et
        VerifyCommand spyCommand = Mockito.spy(verifyCommand);
        doReturn(testUser).when(spyCommand).getAuthenticatedUser();

        when(emailVerificationQueryService.findByUserId(testUser.getId())).thenReturn(emailVerificationDto);
        when(userService.verifyUser(testUser)).thenReturn(verifiedUserDto);

        // Act
        UserDto result = spyCommand.execute(verifyRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.email());
        assertTrue(result.isVerified());

        verify(emailVerificationQueryService).findByUserId(testUser.getId());
        verify(userService).verifyUser(testUser);
        verify(emailVerificationService).deleteEmailVerification(testUser.getId());
    }

    @Test
    void execute_shouldThrowExceptionWhenCodeDoesNotMatch() {
        // Arrange
        EmailVerificationDto mismatchedDto = new EmailVerificationDto(userMapper.toDto(testUser), 654321);

        VerifyCommand spyCommand = Mockito.spy(verifyCommand);
        doReturn(testUser).when(spyCommand).getAuthenticatedUser();

        when(emailVerificationQueryService.findByUserId(testUser.getId())).thenReturn(mismatchedDto);

        // Act & Assert
        assertThrows(EmailVerificationException.class, () -> spyCommand.execute(verifyRequestDto));

        verify(emailVerificationQueryService).findByUserId(testUser.getId());
        verify(userService, never()).verifyUser(any());
        verify(emailVerificationService, never()).deleteEmailVerification(any());
    }
}

