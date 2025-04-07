package org.deha.be.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AfadEarthquakeApiResponse;
import org.deha.be.mapper.EarthquakeMapper;
import org.deha.be.model.Alarm;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.repository.EarthquakeRepository;
import org.deha.be.service.domain.alarm.command.FindAlarmsToWarnCommand;
import org.deha.be.service.rest.AlarmRestClient;
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
    private final FindAlarmsToWarnCommand findAlarmsToWarnCommand;
    private final EarthquakeRepository earthquakeRepository;
    private final EarthquakeMapper earthquakeMapper;

    @Scheduled(fixedDelay = SCHEDULE_DELAY)
    public void execute() throws ExecutionException, InterruptedException {
        CompletableFuture<List<AfadEarthquakeApiResponse>> response = restClient.fetchLastEarthquakes();
        List<Earthquake> earthquakes = response.get()
                .stream()
                .map(earthquakeMapper::toEntity)
                .toList();
        earthquakeRepository.saveAll(earthquakes);
        List<Alarm> alarms = findAlarmsToWarnCommand.execute(earthquakes);
    }
}
