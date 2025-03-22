package me.utku.dehabe.rest;

import me.utku.dehabe.dto.alarm.EarthquakeResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CompletableFuture;

@Component
public class AlarmRestClient {
    private final RestClient restClient;

    public AlarmRestClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://servisnet.afad.gov.tr/apigateway/deprem/apiv2/event/filter")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Async
    public CompletableFuture<EarthquakeResponse> fetchNewEarthquakes() {
        EarthquakeResponse response = restClient.get()
                .retrieve()
                .body(EarthquakeResponse.class);
        return CompletableFuture.completedFuture(response);
    }
}
