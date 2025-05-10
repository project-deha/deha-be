package org.deha.be.service.domain.user.command;

import org.deha.be.dto.user.RegisterRequestParameters;
import org.deha.be.enums.ExceptionDescription;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.deha.be.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserCommandTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private CreateUserCommand createUserCommand;

    @BeforeEach
    public void setUp() {
        // Get the generated mapper implementation
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        createUserCommand = new CreateUserCommand(userRepository, userMapper, bCryptPasswordEncoder);
    }

    @Test
    public void execute_WithValidRequest_ShouldReturnUserDto() {
        // Arrange
        RegisterRequestParameters registerRequestParameters = new RegisterRequestParameters(
                "john@example.com",
                "John",
                "Doe",
                "password123"
        );

        // Mock repository behavior
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Configure the mock to capture and return the saved user
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(UUID.randomUUID());  // Simulate database saving with ID assignment
            return savedUser;
        });

        // Act
        User result = createUserCommand.execute(registerRequestParameters);

        // Assert
        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());

        // Verify interactions
        verify(userRepository).existsByEmail("john@example.com");
        verify(bCryptPasswordEncoder).encode("password123");
        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void execute_WithExistingEmail_ShouldThrowException() {
        // Arrange
        RegisterRequestParameters registerRequestParameters = new RegisterRequestParameters(
                "existing@example.com",
                "John",
                "Doe",
                "password123"
        );

        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // Act & Assert
        DataIntegrityViolationException exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> createUserCommand.execute(registerRequestParameters)
        );

        assertEquals(ExceptionDescription.USER_ALREADY_EXISTS.getValue(), exception.getMessage());

        // Verify interactions
        verify(userRepository).existsByEmail("existing@example.com");
        verify(bCryptPasswordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}