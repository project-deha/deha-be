package me.utku.dehabe.dto.earthquake;

import me.utku.dehabe.enums.TurkishCity;

import java.time.Instant;

public record EarthquakeFilterDto(float minMagnitude,
                                  float maxMagnitude,
                                  TurkishCity city,
                                  Instant startDate,
                                  Instant endDate,
                                  int page,
                                  int size) {
}
