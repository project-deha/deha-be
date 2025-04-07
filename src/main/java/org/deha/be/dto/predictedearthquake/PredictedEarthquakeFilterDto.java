package org.deha.be.dto.predictedearthquake;

import org.deha.be.enums.TurkishCity;

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
