package org.deha.be.service.domain.alarm.command;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AlarmFilterDto;
import org.deha.be.generic.command.Command;
import org.deha.be.model.Alarm;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.service.domain.alarm.query.FilterAlarmQuery;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtractAlarmsToWarnCommand implements Command<HashMap<Earthquake, List<Alarm>>, List<Earthquake>> {
    private final FilterAlarmQuery filterAlarmQuery;

    @Override
    public HashMap<Earthquake, List<Alarm>> execute(List<Earthquake> earthquakes) {
        HashMap<Earthquake, List<Alarm>> alarmsToWarn = new HashMap<>();
        earthquakes.forEach(earthquake -> findAlarmsToWarn(earthquake, alarmsToWarn));
        return alarmsToWarn;
    }

    private void findAlarmsToWarn(Earthquake earthquake, HashMap<Earthquake, List<Alarm>> alarmsToWarn) {
        AlarmFilterDto alarmFilterDto = createAlarmFilterDto(earthquake);
        List<Alarm> alarmsToAddWarningList = filterAlarmQuery.query(alarmFilterDto);
        alarmsToWarn.put(earthquake, alarmsToAddWarningList);
    }

    private AlarmFilterDto createAlarmFilterDto(Earthquake earthquake) {
        return new AlarmFilterDto(
                earthquake.getMagnitude(),
                earthquake.getLocation().getCity()
        );
    }
}
