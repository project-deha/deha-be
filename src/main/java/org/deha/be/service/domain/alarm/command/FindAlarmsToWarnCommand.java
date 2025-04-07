package org.deha.be.service.domain.alarm.command;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AlarmFilterDto;
import org.deha.be.generic.Command;
import org.deha.be.model.Alarm;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.service.domain.alarm.query.FilterAlarmQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAlarmsToWarnCommand implements Command<List<Alarm>, List<Earthquake>> {
    private final FilterAlarmQuery filterAlarmQuery;

    @Override
    public List<Alarm> execute(List<Earthquake> earthquakes) {
        List<Alarm> alarmsToWarn = new ArrayList<>();
        earthquakes.forEach(earthquake -> findAlarmsToWarn(earthquake, alarmsToWarn));
        return alarmsToWarn;
    }

    private void findAlarmsToWarn(Earthquake earthquake, List<Alarm> alarmsToWarn) {
        AlarmFilterDto alarmFilterDto = createAlarmFilterDto(earthquake);
        List<Alarm> alarmsToAddWarningList = filterAlarmQuery.query(alarmFilterDto);
        alarmsToWarn.addAll(alarmsToAddWarningList);
    }

    private AlarmFilterDto createAlarmFilterDto(Earthquake earthquake) {
        return new AlarmFilterDto(
                earthquake.getMagnitude(),
                earthquake.getLocation().getCity()
        );
    }
}
