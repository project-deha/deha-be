package org.deha.be.dto.earthquake;

import java.time.LocalDate;

public interface MonthlyEarthquakeStatsDto {
    LocalDate getDate();

    float getAvgMagnitude();

    int getCount();
}
