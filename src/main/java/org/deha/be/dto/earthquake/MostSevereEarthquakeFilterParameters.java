package org.deha.be.dto.earthquake;

import org.deha.be.enums.TurkishCity;

import java.time.Instant;
import java.util.List;

public record MostSevereEarthquakeFilterParameters(Instant startDate, Instant endDate, List<TurkishCity> city,
                                                   float minMagnitude,
                                                   float maxMagnitude) {
}
