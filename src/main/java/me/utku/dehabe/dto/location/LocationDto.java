package me.utku.dehabe.dto.location;

import me.utku.dehabe.enums.TurkishCity;
import me.utku.dehabe.model.Location;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Location}
 */
public record LocationDto(UUID id, TurkishCity city, float latitude, float longitude) implements Serializable {
}