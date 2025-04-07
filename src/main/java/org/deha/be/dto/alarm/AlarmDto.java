package org.deha.be.dto.alarm;

import org.deha.be.dto.location.LocationDto;
import org.deha.be.dto.user.UserDto;
import org.deha.be.model.Alarm;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Alarm}
 */
public record AlarmDto(UUID id, UserDto owner, float minimumMagnitude, float maximumMagnitude,
                       LocationDto location) implements Serializable {
}