package org.deha.be.dto.earthquake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyEarthquakeStatsDto {
    private Instant date;
    private float avgMagnitude;
    private int count;
}
