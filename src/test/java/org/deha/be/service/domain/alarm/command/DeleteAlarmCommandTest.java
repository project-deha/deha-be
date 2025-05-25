package org.deha.be.service.domain.alarm.command;

import jakarta.persistence.EntityNotFoundException;
import org.deha.be.dto.alarm.DeleteAlarmParameters;
import org.deha.be.model.Alarm;
import org.deha.be.model.User;
import org.deha.be.repository.AlarmRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAlarmCommandTest {

    @Mock
    private AlarmRepository alarmRepository;

    @InjectMocks
    private DeleteAlarmCommand deleteAlarmCommand;

    private UUID alarmId;
    private UUID userId;
    private UUID ownerId;
    private User user;
    private User owner;
    private Alarm alarm;

    @BeforeEach
    void setUp() {
        alarmId = UUID.randomUUID();
        userId = UUID.randomUUID();
        ownerId = UUID.randomUUID();

        user = new User();
        user.setId(userId);

        owner = new User();
        owner.setId(ownerId);

        alarm = new Alarm();
        alarm.setId(alarmId);
        alarm.setOwner(owner);
    }

    @Test
    void execute_WhenAlarmNotFound_ShouldThrowEntityNotFoundException() {
        // Arrange
        DeleteAlarmParameters parameters = new DeleteAlarmParameters(user, alarmId);
        when(alarmRepository.findById(alarmId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> deleteAlarmCommand.execute(parameters));
        verify(alarmRepository, never()).delete(Mockito.any(Alarm.class));
    }

    @Test
    void execute_WhenUserIsNotOwner_ShouldThrowIllegalArgumentException() {
        // Arrange
        DeleteAlarmParameters parameters = new DeleteAlarmParameters(user, alarmId);
        when(alarmRepository.findById(alarmId)).thenReturn(Optional.of(alarm));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deleteAlarmCommand.execute(parameters));
        verify(alarmRepository, never()).delete(any(Alarm.class));
    }

    @Test
    void execute_WhenUserIsOwner_ShouldDeleteAlarm() {
        // Arrange
        user.setId(ownerId); // Make user the owner
        DeleteAlarmParameters parameters = new DeleteAlarmParameters(user, alarmId);
        when(alarmRepository.findById(alarmId)).thenReturn(Optional.of(alarm));

        // Act
        deleteAlarmCommand.execute(parameters);

        // Assert
        verify(alarmRepository, times(1)).delete(alarm);
    }
}