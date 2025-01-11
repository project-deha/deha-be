package me.utku.dehabe.dto.predictedearthquake;

import java.time.Instant;

public record PredictedEarthquakeFilterDto(
        float minMagnitude,
        float maxMagnitude,
        String city,
        Instant startDate,
        Instant endDate,
        int page,
        int size
) {
}
