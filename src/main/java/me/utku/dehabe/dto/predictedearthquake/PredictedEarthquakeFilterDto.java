package me.utku.dehabe.dto.predictedearthquake;

import me.utku.dehabe.enums.TurkishCity;

import java.time.Instant;

public record PredictedEarthquakeFilterDto(
        Float minMagnitude,
        Float maxMagnitude,
        TurkishCity city,
        Instant startDate,
        Instant endDate,
        int page,
        int size
) {
}
