package me.utku.dehabe.service.scheduled;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.alarm.AfadEarthquakeApiResponse;
import me.utku.dehabe.dto.alarm.AlarmDto;
import me.utku.dehabe.mapper.EarthquakeMapper;
import me.utku.dehabe.model.earthquakes.Earthquake;
import me.utku.dehabe.repository.EarthquakeRepository;
import me.utku.dehabe.service.domain.alarm.command.FindAlarmsToWarnCommand;
import me.utku.dehabe.service.rest.AlarmRestClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class EarthquakeAlarmTask {
    private static final int SCHEDULE_DELAY = 1000 * 60 * 5;
    private final AlarmRestClient restClient;
    private final EarthquakeMapper earthquakeMapper;
    private final FindAlarmsToWarnCommand findAlarmsToWarnCommand;
    private final EarthquakeRepository earthquakeRepository;

    @Scheduled(fixedDelay = SCHEDULE_DELAY)
    public void execute() throws ExecutionException, InterruptedException {
        CompletableFuture<List<AfadEarthquakeApiResponse>> response = restClient.fetchLastEarthquakes();
        List<Earthquake> earthquakes = response.get().stream().map(earthquakeMapper::toEntity).toList();
        earthquakeRepository.saveAll(earthquakes);
        List<AlarmDto> alarms = findAlarmsToWarnCommand.execute(earthquakes);
    }
}
