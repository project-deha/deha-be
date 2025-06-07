package org.deha.be.dto.predictedearthquake;

public record PredictionWeightByMagnitudeDto(String magnitudeRange, long earthquakeCount) {
}
