package me.utku.dehabe.service.domain.alarm.command;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.alarm.AlarmDto;
import me.utku.dehabe.dto.alarm.AlarmFilterDto;
import me.utku.dehabe.generic.Command;
import me.utku.dehabe.mapper.AlarmMapper;
import me.utku.dehabe.model.earthquakes.Earthquake;
import me.utku.dehabe.service.domain.alarm.query.FilterAlarmQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAlarmsToWarnCommand implements Command<List<AlarmDto>, List<Earthquake>> {
    private final FilterAlarmQuery filterAlarmQuery;
    private final AlarmMapper alarmMapper;

    @Override
    public List<AlarmDto> execute(List<Earthquake> earthquakes) {
        List<AlarmDto> alarmsToWarn = new ArrayList<>();
        earthquakes.forEach(earthquake -> findAlarmsToWarn(earthquake, alarmsToWarn));
        return alarmsToWarn;
    }

    private void findAlarmsToWarn(Earthquake earthquake, List<AlarmDto> alarmsToWarn) {
        AlarmFilterDto alarmFilterDto = createAlarmFilterDto(earthquake);
        List<AlarmDto> alarmsToAddWarningList = filterAlarmQuery.query(alarmFilterDto)
                .stream()
                .map(alarmMapper::toDto)
                .toList();
        alarmsToWarn.addAll(alarmsToAddWarningList);
    }

    private AlarmFilterDto createAlarmFilterDto(Earthquake earthquake) {
        return new AlarmFilterDto(
                earthquake.getMagnitude(),
                earthquake.getLocation().getCity()
        );
    }
}
