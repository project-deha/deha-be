package me.utku.dehabe.service.alarm.command;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.alarm.AfadEarthquakeApiResponse;
import me.utku.dehabe.dto.alarm.AlarmDto;
import me.utku.dehabe.dto.alarm.AlarmFilterDto;
import me.utku.dehabe.mapper.EarthquakeMapper;
import me.utku.dehabe.model.earthquakes.Earthquake;
import me.utku.dehabe.rest.AlarmRestClient;
import me.utku.dehabe.service.alarm.query.FilterAlarmQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FetchLastEarthquakesCommand {
    private static final int SCHEDULE_DELAY = 1000 * 60 * 5;
    private final AlarmRestClient restClient;
    private final FilterAlarmQuery filterAlarmQuery;
    private final EarthquakeMapper earthquakeMapper;

    @Scheduled(fixedDelay = SCHEDULE_DELAY)
    public void execute() throws ExecutionException, InterruptedException {
        CompletableFuture<List<AfadEarthquakeApiResponse>> response = restClient.fetchLastEarthquakes();
        List<Earthquake> earthquakes = response.get().stream().map(earthquakeMapper::toEntity).toList();
        List<AlarmDto> alarmsToWarn = new ArrayList<>();

        earthquakes.forEach(earthquake -> findAlarmsToWarn(earthquake, alarmsToWarn));
    }

    private void findAlarmsToWarn(Earthquake earthquake, List<AlarmDto> alarmsToWarn) {
        AlarmFilterDto alarmFilterDto = createAlarmFilterDto(earthquake);
        List<AlarmDto> alarmsToAddWarningList = filterAlarmQuery.query(alarmFilterDto);
        alarmsToWarn.addAll(alarmsToAddWarningList);
    }

    private AlarmFilterDto createAlarmFilterDto(Earthquake earthquake) {
        return new AlarmFilterDto(
                earthquake.getMagnitude(),
                earthquake.getLocation().getCity()
        );
    }
}
