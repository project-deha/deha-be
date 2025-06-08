package org.deha.be.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.deha.be.model.Alarm;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.repository.EarthquakeRepository;
import org.deha.be.service.domain.alarm.command.ExtractAlarmsToWarnCommand;
import org.deha.be.service.email.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class EarthquakeAlarmTask {
    private static final int SCHEDULE_DELAY = 1000 * 60 * 5;
    private final ExtractAlarmsToWarnCommand extractAlarmsToWarnCommand;
    private final EarthquakeRepository earthquakeRepository;
    private final EmailService emailService;

    @Scheduled(fixedDelay = SCHEDULE_DELAY)
    public void execute() throws ExecutionException, InterruptedException {
        List<Earthquake> earthquakes = earthquakeRepository.findAllByOccurrenceDateAfterAndIsAlarmed(
                LocalDateTime.now().minusMinutes(30).toInstant(ZoneOffset.UTC),
                false
        );
        HashMap<Earthquake, List<Alarm>> alarmsToWarn = extractAlarmsToWarnCommand.execute(earthquakes);
        alarmsToWarn.forEach((earthquake, alarms) -> {
                    alarms.forEach(alarm -> emailService.sendEarthquakeEmail(earthquake, alarm));
                    earthquake.setAlarmed(true);
                    earthquakeRepository.save(earthquake);
                }
        );
    }
}
