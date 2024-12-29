package me.utku.dehabe.dto.predictedearthquake;

public record PredictedEarthquakeFilterDto(
        float minMagnitude,
        float maxMagnitude,
        float minDepth,
        float maxDepth,
        String city,
        float minPossibility,
        float maxPossibility,
        int page,
        int size
) {
}
