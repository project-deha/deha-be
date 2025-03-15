package me.utku.dehabe.model;

import me.utku.dehabe.dto.location.LocationDto;
import me.utku.dehabe.dto.user.UserDto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Alarm}
 */
public record AlarmDto(UUID id, UserDto owner, float minimumMagnitude, float maximumMagnitude,
                       LocationDto location) implements Serializable {
}