package me.utku.dehabe.dto.earthquake;

import me.utku.dehabe.dto.location.LocationDto;

import java.time.Instant;
import java.util.UUID;

public record EarthquakeDto(UUID id,
                            float magnitude,
                            float depth,
                            LocationDto location,
                            Instant occurrenceDate) {
}
