package org.deha.be.dto.predictedearthquake;

import org.deha.be.enums.TurkishCity;

import java.time.Instant;
import java.util.List;

public record MostSeverePredictedEarthquakeFilterParameters(
        Instant startDate, Instant endDate, List<TurkishCity> city,
        float minMagnitude,
        float maxMagnitude
) {
}
