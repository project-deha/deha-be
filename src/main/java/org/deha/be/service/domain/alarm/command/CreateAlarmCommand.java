package org.deha.be.service.domain.alarm.command;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.CreateAlarmParameters;
import org.deha.be.generic.command.Command;
import org.deha.be.mapper.AlarmMapper;
import org.deha.be.model.Alarm;
import org.deha.be.repository.AlarmRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAlarmCommand implements Command<Alarm, CreateAlarmParameters> {
    private final AlarmRepository alarmRepository;
    private final AlarmMapper alarmMapper;

    @Override
    public Alarm execute(CreateAlarmParameters parameters) {
        Alarm alarm = alarmMapper.toEntity(parameters.alarmDto());
        alarm.setOwner(parameters.user());
        return alarmRepository.save(alarm);
    }
}
