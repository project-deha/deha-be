package org.deha.be.service.rest;

import org.deha.be.dto.alarm.AFADApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class AlarmRestClient {
    private static final int TIME_INTERVAL = 5;
    private final RestClient restClient;

    public AlarmRestClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://deprem.afad.gov.tr/apiv2/event/filter/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Async
    public CompletableFuture<List<AFADApiResponse>> fetchLastEarthquakes() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        AFADApiResponse[] AFADApiResponse = restClient.get()
                .uri("?start={startDate}&end={endDate}&format=json",
                        now.minusMinutes(TIME_INTERVAL).format(formatter),
                        now.format(formatter)
                )
                .retrieve()
                .body(AFADApiResponse[].class);
        return CompletableFuture.completedFuture(List.of(AFADApiResponse));
    }
}
