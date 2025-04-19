package org.deha.be.service.domain.alarm;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.*;
import org.deha.be.mapper.AlarmMapper;
import org.deha.be.model.Alarm;
import org.deha.be.model.User;
import org.deha.be.service.domain.alarm.command.CreateAlarmsCommand;
import org.deha.be.service.domain.alarm.command.DeleteAlarmCommand;
import org.deha.be.service.domain.alarm.command.UpdateAlarmCommand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final CreateAlarmsCommand createAlarmsCommand;
    private final UpdateAlarmCommand updateAlarmCommand;
    private final DeleteAlarmCommand deleteAlarmCommand;
    private final AlarmMapper alarmMapper;

    public List<AlarmDto> createAlarms(User user, CreateAlarmDto createAlarmDto) {
        CreateAlarmParameters parameters = new CreateAlarmParameters(user, createAlarmDto);
        List<Alarm> createdAlarms = createAlarmsCommand.execute(parameters);
        return createdAlarms.stream().map(alarmMapper::toDto).toList();
    }

    public AlarmDto updateAlarm(User user, AlarmDto alarmDto) {
        UpdateAlarmParameters parameters = new UpdateAlarmParameters(user, alarmDto);
        Alarm updatedAlarm = updateAlarmCommand.execute(parameters);
        return alarmMapper.toDto(updatedAlarm);
    }

    public void deleteAlarm(User user, UUID alarmId) {
        DeleteAlarmParameters parameters = new DeleteAlarmParameters(user, alarmId);
        deleteAlarmCommand.execute(parameters);
    }
}
