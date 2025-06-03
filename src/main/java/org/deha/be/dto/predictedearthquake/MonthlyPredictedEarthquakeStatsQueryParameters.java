package org.deha.be.dto.predictedearthquake;

import java.time.LocalDate;

public record MonthlyPredictedEarthquakeStatsQueryParameters(LocalDate startDate, LocalDate endDate) {
}
