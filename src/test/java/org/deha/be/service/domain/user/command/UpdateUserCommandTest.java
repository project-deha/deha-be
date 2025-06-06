package org.deha.be.service.domain.user.command;

import org.deha.be.dto.user.UpdateUserRequestParameters;
import org.deha.be.dto.user.UserDto;
import org.deha.be.mapper.UserMapper;
import org.deha.be.model.User;
import org.deha.be.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UpdateUserCommandTest {
    @Mock
    private UserRepository userRepository;
    private UpdateUserCommand updateUserCommand;

    @BeforeEach
    public void setUp() {
        // Get the generated mapper implementation
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        updateUserCommand = new UpdateUserCommand(userMapper, userRepository);
    }

    @Test
    public void execute_WithCompleteUpdateData_ShouldUpdateAllFields() {
        // Arrange
        User existingUser = new User();
        existingUser.setId(UUID.randomUUID());
        existingUser.setEmail("john@example.com");
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");

        UserDto updateData = new UserDto(
                UUID.randomUUID(),
                "johnny@example.com",  // Update email
                "Johnny",              // Update first name
                "Smith",
                null,
                false
        );

        UpdateUserRequestParameters updateUserRequestParameters = new UpdateUserRequestParameters(
                updateData,
                existingUser
        );

        // Act
        User result = updateUserCommand.execute(updateUserRequestParameters);

        // Assert
        assertNotNull(result);
        assertEquals("johnny@example.com", result.getEmail());
        assertEquals("Johnny", result.getFirstName());
        assertEquals("Smith", result.getLastName());
    }

    @Test
    public void execute_WithPartialUpdateData_ShouldOnlyUpdateProvidedFields() {
        // Arrange
        User existingUser = new User();
        existingUser.setId(UUID.randomUUID());
        existingUser.setEmail("john@example.com");
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");

        UserDto updateData = new UserDto(
                null,
                null, // Don't update email,
                "Johnny",   // Update first name
                null,
                null,
                false
        );

        UpdateUserRequestParameters updateUserRequestParameters = new UpdateUserRequestParameters(
                updateData,
                existingUser
        );

        // Act
        User result = updateUserCommand.execute(updateUserRequestParameters);

        // Assert
        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());  // Should remain unchanged
        assertEquals("Johnny", result.getFirstName());        // Should be updated
        assertEquals("Doe", result.getLastName());            // Should remain unchanged
    }

    @Test
    public void execute_WithEmptyUpdateData_ShouldNotChangeAnyFields() {
        // Arrange
        User existingUser = new User();
        existingUser.setId(UUID.randomUUID());
        existingUser.setEmail("john@example.com");
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");

        UserDto updateData = new UserDto(
                null,  // No updates
                null,
                null,
                null,
                null,
                false
        );

        UpdateUserRequestParameters updateUserRequestParameters = new UpdateUserRequestParameters(
                updateData,
                existingUser
        );

        // Act
        User result = updateUserCommand.execute(updateUserRequestParameters);

        // Assert
        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }
}