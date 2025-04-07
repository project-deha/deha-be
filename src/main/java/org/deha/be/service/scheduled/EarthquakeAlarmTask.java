package org.deha.be.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.alarm.AFADApiResponse;
import org.deha.be.mapper.EarthquakeMapper;
import org.deha.be.model.Alarm;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.repository.EarthquakeRepository;
import org.deha.be.service.domain.alarm.command.ExtractAlarmsToWarnCommand;
import org.deha.be.service.email.EmailService;
import org.deha.be.service.rest.AlarmRestClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class EarthquakeAlarmTask {
    private static final int SCHEDULE_DELAY = 1000 * 60 * 5;
    private final AlarmRestClient restClient;
    private final ExtractAlarmsToWarnCommand extractAlarmsToWarnCommand;
    private final EarthquakeRepository earthquakeRepository;
    private final EarthquakeMapper earthquakeMapper;
    private final EmailService emailService;

    @Scheduled(fixedDelay = SCHEDULE_DELAY)
    public void execute() throws ExecutionException, InterruptedException {
        CompletableFuture<List<AFADApiResponse>> response = restClient.fetchLastEarthquakes();
        List<Earthquake> earthquakes = response.get()
                .stream()
                .map(earthquakeMapper::toEntity)
                .toList();
        earthquakeRepository.saveAll(earthquakes);
        HashMap<Earthquake, List<Alarm>> alarmsToWarn = extractAlarmsToWarnCommand.execute(earthquakes);
        alarmsToWarn.forEach((earthquake, alarms) -> alarms
                .forEach(alarm -> emailService.sendEarthquakeEmail(earthquake, alarm))
        );
    }
}
