package org.deha.be.dto.location;

import org.deha.be.enums.TurkishCity;
import org.deha.be.model.Location;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Location}
 */
public record LocationDto(UUID id, TurkishCity city, float latitude, float longitude) implements Serializable {
}