package me.utku.dehabe.dto.alarm;

import me.utku.dehabe.dto.location.LocationDto;
import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.model.Alarm;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Alarm}
 */
public record AlarmDto(UUID id, UserDto owner, float minimumMagnitude, float maximumMagnitude,
                       LocationDto location) implements Serializable {
}