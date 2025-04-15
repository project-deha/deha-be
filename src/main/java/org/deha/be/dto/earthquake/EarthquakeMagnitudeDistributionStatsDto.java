package org.deha.be.dto.earthquake;

public interface EarthquakeMagnitudeDistributionStatsDto {
    String getRange();

    long getCount();

    double getPercentage();
}