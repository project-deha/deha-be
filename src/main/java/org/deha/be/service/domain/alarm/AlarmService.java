package org.deha.be.service.domain.alarm;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AlarmDto;
import org.deha.be.dto.alarm.CreateAlarmParameters;
import org.deha.be.mapper.AlarmMapper;
import org.deha.be.model.Alarm;
import org.deha.be.model.User;
import org.deha.be.service.domain.alarm.command.CreateAlarmCommand;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final CreateAlarmCommand createAlarmCommand;
    private final AlarmMapper alarmMapper;

    public AlarmDto createAlarm(User user, AlarmDto alarmDto) {
        CreateAlarmParameters parameters = new CreateAlarmParameters(user, alarmDto);
        Alarm createdAlarm = createAlarmCommand.execute(parameters);
        return alarmMapper.toDto(createdAlarm);
    }
}
