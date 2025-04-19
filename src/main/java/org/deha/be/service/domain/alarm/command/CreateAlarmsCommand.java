package org.deha.be.service.domain.alarm.command;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.CreateAlarmParameters;
import org.deha.be.generic.command.Command;
import org.deha.be.model.Alarm;
import org.deha.be.repository.AlarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateAlarmsCommand implements Command<List<Alarm>, CreateAlarmParameters> {
    private final AlarmRepository alarmRepository;

    @Override
    public List<Alarm> execute(CreateAlarmParameters parameters) {
        return parameters.createAlarmDto().cities().stream().map(city -> {
            Alarm alarm = new Alarm();
            alarm.setMinimumMagnitude(parameters.createAlarmDto().minimumMagnitude());
            alarm.setMaximumMagnitude(parameters.createAlarmDto().maximumMagnitude());
            alarm.setOwner(parameters.user());
            alarm.setCity(city);
            alarm.setActive(true);
            return alarmRepository.save(alarm);
        }).toList();
    }
}
