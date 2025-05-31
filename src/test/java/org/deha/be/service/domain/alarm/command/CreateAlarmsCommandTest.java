package org.deha.be.service.domain.alarm.command;

import org.deha.be.dto.alarm.CreateAlarmDto;
import org.deha.be.dto.alarm.CreateAlarmParameters;
import org.deha.be.enums.Role;
import org.deha.be.enums.TurkishCity;
import org.deha.be.model.Alarm;
import org.deha.be.model.User;
import org.deha.be.repository.AlarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAlarmsCommandTest {

    @Mock
    private AlarmRepository alarmRepository;

    @InjectMocks
    private CreateAlarmsCommand createAlarmsCommand;

    @Captor
    private ArgumentCaptor<Alarm> alarmCaptor;

    private User testUser;
    private CreateAlarmDto createAlarmDto;
    private CreateAlarmParameters parameters;

    @BeforeEach
    void setUp() {
        // Setup test user
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setAuthorities(Set.of(Role.ROLE_UNVALIDATED_RESEARCHER));

        // Setup CreateAlarmDto with TurkishCity enum values
        createAlarmDto = new CreateAlarmDto(
                4.5f, 7f,
                List.of(TurkishCity.İSTANBUL, TurkishCity.ANKARA, TurkishCity.İZMİR)
        );

        parameters = new CreateAlarmParameters(testUser, createAlarmDto);
    }

    @Test
    void execute_shouldCreateAndSaveAlarmsForAllCities() {
        // Arrange
        when(alarmRepository.save(any(Alarm.class))).thenAnswer(invocation -> {
            Alarm savedAlarm = invocation.getArgument(0);
            savedAlarm.setId(UUID.randomUUID()); // Simulate DB setting ID
            return savedAlarm;
        });

        // Act
        List<Alarm> result = createAlarmsCommand.execute(parameters);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(alarmRepository, times(3)).save(alarmCaptor.capture());

        List<Alarm> capturedAlarms = alarmCaptor.getAllValues();
        assertEquals(3, capturedAlarms.size());

        List<TurkishCity> cities = capturedAlarms.stream().map(Alarm::getCity).toList();
        assertTrue(cities.containsAll(List.of(TurkishCity.İSTANBUL, TurkishCity.ANKARA, TurkishCity.İZMİR)));

        // Check properties of all alarms
        for (Alarm alarm : capturedAlarms) {
            assertEquals(4.5f, alarm.getMinimumMagnitude());
            assertEquals(7.0f, alarm.getMaximumMagnitude());
            assertEquals(testUser, alarm.getOwner());
            assertTrue(alarm.isActive());
        }
    }

    @Test
    void execute_shouldReturnEmptyListForEmptyCitiesList() {
        // Arrange
        CreateAlarmDto emptyDto = new CreateAlarmDto(
                4.5f,
                7.0f,
                List.of()
        );

        CreateAlarmParameters emptyParameters = new CreateAlarmParameters(testUser, emptyDto);

        // Act
        List<Alarm> result = createAlarmsCommand.execute(emptyParameters);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(alarmRepository, never()).save(any());
    }

    @Test
    void execute_shouldCreateAlarmsWithCorrectMagnitudeRange() {
        // Arrange
        CreateAlarmDto customDto = new CreateAlarmDto(
                3.0f,
                9.5f,
                List.of(TurkishCity.İSTANBUL)
        );

        CreateAlarmParameters customParameters = new CreateAlarmParameters(testUser, customDto);

        when(alarmRepository.save(any(Alarm.class))).thenAnswer(invocation -> {
            Alarm savedAlarm = invocation.getArgument(0);
            savedAlarm.setId(UUID.randomUUID());
            return savedAlarm;
        });

        // Act
        List<Alarm> result = createAlarmsCommand.execute(customParameters);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(alarmRepository).save(alarmCaptor.capture());

        Alarm capturedAlarm = alarmCaptor.getValue();
        assertEquals(3.0f, capturedAlarm.getMinimumMagnitude());
        assertEquals(9.5f, capturedAlarm.getMaximumMagnitude());
    }

    @Test
    void execute_shouldAssociateAlarmsWithCorrectUser() {
        // Arrange
        User anotherUser = new User();
        anotherUser.setId(UUID.randomUUID());
        anotherUser.setEmail("another@example.com");
        anotherUser.setFirstName("Another");
        anotherUser.setLastName("User");
        anotherUser.setAuthorities(Set.of(Role.ROLE_UNVALIDATED_RESEARCHER));

        CreateAlarmParameters customParameters = new CreateAlarmParameters(anotherUser, createAlarmDto);

        when(alarmRepository.save(any(Alarm.class))).thenAnswer(invocation -> {
            Alarm savedAlarm = invocation.getArgument(0);
            savedAlarm.setId(UUID.randomUUID());
            return savedAlarm;
        });

        // Act
        List<Alarm> result = createAlarmsCommand.execute(customParameters);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(alarmRepository, times(3)).save(alarmCaptor.capture());

        List<Alarm> capturedAlarms = alarmCaptor.getAllValues();
        for (Alarm alarm : capturedAlarms) {
            assertEquals(anotherUser, alarm.getOwner());
        }
    }

    @Test
    void execute_shouldSetAlarmsAsActive() {
        // Arrange
        when(alarmRepository.save(any(Alarm.class))).thenAnswer(invocation -> {
            Alarm savedAlarm = invocation.getArgument(0);
            savedAlarm.setId(UUID.randomUUID());
            return savedAlarm;
        });

        // Act
        List<Alarm> result = createAlarmsCommand.execute(parameters);

        // Assert
        assertNotNull(result);
        verify(alarmRepository, times(3)).save(alarmCaptor.capture());

        List<Alarm> capturedAlarms = alarmCaptor.getAllValues();
        for (Alarm alarm : capturedAlarms) {
            assertTrue(alarm.isActive());
        }
    }
}