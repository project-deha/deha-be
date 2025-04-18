package org.deha.be.service.domain.alarm;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AlarmDto;
import org.deha.be.mapper.AlarmMapper;
import org.deha.be.model.Alarm;
import org.deha.be.model.User;
import org.deha.be.repository.AlarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmQueryService {
    private final AlarmRepository alarmRepository;
    private final AlarmMapper alarmMapper;

    public List<AlarmDto> getUserAlarms(User user) {
        List<Alarm> alarm = alarmRepository.findByOwner_Id(user.getId());
        return alarmMapper.toDto(alarm);
    }

}
