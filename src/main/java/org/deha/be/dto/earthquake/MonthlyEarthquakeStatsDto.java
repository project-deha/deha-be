package org.deha.be.dto.earthquake;

import java.time.Instant;

public interface MonthlyEarthquakeStatsDto {
    Instant getDate();

    float getAvgMagnitude();

    int getCount();
}
