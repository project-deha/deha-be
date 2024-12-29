package me.utku.dehabe.dto.predictedearthquake;

import me.utku.dehabe.dto.location.LocationDto;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link PredictedEarthquake}
 */
public record PredictedEarthquakeDto(UUID id, float magnitude, float depth, LocationDto location,
                                     float possibility) implements Serializable {
}