package org.deha.be.service.domain.alarm.command;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.DeleteAlarmParameters;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.Alarm;
import org.deha.be.repository.AlarmRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAlarmCommand implements EffectCommand<DeleteAlarmParameters> {
    private final AlarmRepository alarmRepository;

    @Override
    public void execute(DeleteAlarmParameters parameters) {
        Alarm alarm = alarmRepository.findById(parameters.alarmId()).orElseThrow(EntityNotFoundException::new);
        if (!alarm.getOwner().getId().equals(parameters.user().getId())) {
            throw new IllegalArgumentException("You are not the owner of this alarm");
        }
        alarmRepository.delete(alarm);
    }
}
