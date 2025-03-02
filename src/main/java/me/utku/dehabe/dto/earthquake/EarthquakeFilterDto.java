package me.utku.dehabe.dto.earthquake;

import java.time.Instant;

public record EarthquakeFilterDto(float minMagnitude,
                                  float maxMagnitude,
                                  String city,
                                  Instant startDate,
                                  Instant endDate,
                                  int page,
                                  int size) {
}
