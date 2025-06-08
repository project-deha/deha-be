package org.deha.be.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AFADApiResponse;
import org.deha.be.mapper.EarthquakeMapper;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.repository.EarthquakeRepository;
import org.deha.be.service.rest.AlarmRestClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FetchLastEarthquakesTask {
    private static final int SCHEDULE_DELAY = 1000 * 60 * 5;
    private final AlarmRestClient alarmRestClient;
    private final EarthquakeMapper earthquakeMapper;
    private final EarthquakeRepository earthquakeRepository;

    @Scheduled(fixedDelay = SCHEDULE_DELAY)
    public void execute() throws ExecutionException, InterruptedException {
        CompletableFuture<List<AFADApiResponse>> response = alarmRestClient.fetchLastEarthquakes();
        List<Earthquake> earthquakes = response.get()
                .stream()
                .map(earthquakeMapper::toEntity)
                .toList();

        if (earthquakes.isEmpty()) {
            return;
        }

        earthquakes.forEach(earthquake -> {
            if (!earthquakeRepository.existsByEventID(earthquake.getEventID())) {
                earthquakeRepository.save(earthquake);
            }
        });
    }
}
