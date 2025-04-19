package org.deha.be.service.domain.alarm;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AlarmDto;
import org.deha.be.dto.alarm.CreateAlarmDto;
import org.deha.be.dto.alarm.CreateAlarmParameters;
import org.deha.be.mapper.AlarmMapper;
import org.deha.be.model.Alarm;
import org.deha.be.model.User;
import org.deha.be.service.domain.alarm.command.CreateAlarmsCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final CreateAlarmsCommand createAlarmsCommand;
    private final AlarmMapper alarmMapper;

    public List<AlarmDto> createAlarms(User user, CreateAlarmDto createAlarmDto) {
        CreateAlarmParameters parameters = new CreateAlarmParameters(user, createAlarmDto);
        List<Alarm> createdAlarms = createAlarmsCommand.execute(parameters);
        return createdAlarms.stream().map(alarmMapper::toDto).toList();
    }
}
