package org.deha.be.dto.predictedearthquake;

import org.deha.be.dto.location.LocationDto;
import org.deha.be.model.earthquakes.PredictedEarthquake;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link PredictedEarthquake}
 */
public record PredictedEarthquakeDto(UUID id,
                                     float magnitude,
                                     float depth,
                                     LocationDto location,
                                     Instant occurrenceDate) {
}