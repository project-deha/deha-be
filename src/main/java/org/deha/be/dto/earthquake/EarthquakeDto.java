package org.deha.be.dto.earthquake;

import org.deha.be.dto.location.LocationDto;

import java.time.Instant;
import java.util.UUID;

public record EarthquakeDto(UUID id,
                            float magnitude,
                            float depth,
                            LocationDto location,
                            Instant occurrenceDate) {
}
