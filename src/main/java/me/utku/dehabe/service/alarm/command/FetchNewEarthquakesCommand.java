package me.utku.dehabe.service.alarm.command;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.rest.AlarmRestClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchNewEarthquakesCommand {
    private final AlarmRestClient restClient;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void execute() {
        restClient.fetchNewEarthquakes();
    }
}
