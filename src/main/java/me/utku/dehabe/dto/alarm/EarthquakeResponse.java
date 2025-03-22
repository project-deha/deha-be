package me.utku.dehabe.dto.alarm;

import java.time.Instant;

public record EarthquakeResponse(
        long eventID,
        String location,
        float latitude,
        float longitude,
        float depth,
        float magnitude,
        String province,
        Instant date
) {
}
