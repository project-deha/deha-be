package org.deha.be.dto.earthquake;

import org.deha.be.enums.TurkishCity;

import java.time.Instant;

public record EarthquakeFilterDto(float minMagnitude,
                                  float maxMagnitude,
                                  TurkishCity city,
                                  Instant startDate,
                                  Instant endDate,
                                  int page,
                                  int size) {
}
