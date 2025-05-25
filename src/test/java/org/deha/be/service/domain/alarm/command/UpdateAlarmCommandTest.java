package org.deha.be.service.domain.alarm.command;

import jakarta.persistence.EntityNotFoundException;
import org.deha.be.dto.alarm.AlarmDto;
import org.deha.be.dto.alarm.UpdateAlarmParameters;
import org.deha.be.dto.user.UserDto;
import org.deha.be.enums.TurkishCity;
import org.deha.be.mapper.AlarmMapper;
import org.deha.be.model.Alarm;
import org.deha.be.model.User;
import org.deha.be.repository.AlarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAlarmCommandTest {

    @Mock
    private AlarmRepository alarmRepository;

    @Mock
    private AlarmMapper alarmMapper;

    @InjectMocks
    private UpdateAlarmCommand updateAlarmCommand;

    private User user;
    private AlarmDto alarmDto;
    private Alarm existingAlarm;
    private Alarm updatedAlarm;
    private UpdateAlarmParameters parameters;
    private UUID alarmId;

    @BeforeEach
    void setUp() {
        // Test verileri oluşturma
        alarmId = UUID.randomUUID();

        user = new User();
        user.setId(UUID.randomUUID());

        UserDto userDto = new UserDto(
                user.getId(),
                "test@example.com",
                "Test",
                "User",
                null,
                true
        );

        alarmDto = new AlarmDto(
                alarmId,                   // UUID id
                userDto,                   // UserDto owner
                5.0f,                      // float minimumMagnitude
                7.5f,                      // float maximumMagnitude
                TurkishCity.İSTANBUL,      // TurkishCity city
                true                       // boolean isActive
        );

        existingAlarm = new Alarm();
        existingAlarm.setId(alarmId);
        existingAlarm.setMinimumMagnitude(4.0f);
        existingAlarm.setMaximumMagnitude(8.0f);
        existingAlarm.setCity(TurkishCity.ANKARA);
        existingAlarm.setActive(false);
        existingAlarm.setOwner(user);

        updatedAlarm = new Alarm();
        updatedAlarm.setId(alarmId);
        updatedAlarm.setMinimumMagnitude(5.0f);
        updatedAlarm.setMaximumMagnitude(7.5f);
        updatedAlarm.setCity(TurkishCity.İSTANBUL);
        updatedAlarm.setActive(true);
        updatedAlarm.setOwner(user);

        parameters = new UpdateAlarmParameters(user, alarmDto);
    }

    @Test
    void execute_ShouldUpdateAndReturnAlarm_WhenAlarmExists() {
        // Given
        when(alarmRepository.findOneByOwner_IdAndId(user.getId(), alarmDto.id()))
                .thenReturn(Optional.of(existingAlarm));
        when(alarmMapper.partialUpdate(alarmDto, existingAlarm)).thenReturn(updatedAlarm);
        when(alarmRepository.save(updatedAlarm)).thenReturn(updatedAlarm);

        // When
        Alarm result = updateAlarmCommand.execute(parameters);

        // Then
        assertNotNull(result);
        assertEquals(updatedAlarm.getId(), result.getId());
        assertEquals(updatedAlarm.getMinimumMagnitude(), result.getMinimumMagnitude());
        assertEquals(updatedAlarm.getMaximumMagnitude(), result.getMaximumMagnitude());
        assertEquals(updatedAlarm.getCity(), result.getCity());
        assertEquals(updatedAlarm.isActive(), result.isActive());

        verify(alarmRepository).findOneByOwner_IdAndId(user.getId(), alarmDto.id());
        verify(alarmMapper).partialUpdate(alarmDto, existingAlarm);
        verify(alarmRepository).save(updatedAlarm);
    }

    @Test
    void execute_ShouldThrowEntityNotFoundException_WhenAlarmDoesNotExist() {
        // Given
        when(alarmRepository.findOneByOwner_IdAndId(user.getId(), alarmDto.id()))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            updateAlarmCommand.execute(parameters);
        });

        verify(alarmRepository).findOneByOwner_IdAndId(user.getId(), alarmDto.id());
        verify(alarmMapper, never()).partialUpdate(any(), any());
        verify(alarmRepository, never()).save(any());
    }
}