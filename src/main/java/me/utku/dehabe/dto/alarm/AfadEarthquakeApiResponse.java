package me.utku.dehabe.dto.alarm;

import java.time.LocalDateTime;

public record AfadEarthquakeApiResponse(long eventID,
                                        String location,
                                        float latitude,
                                        float longitude,
                                        float depth,
                                        float magnitude,
                                        String province,
                                        LocalDateTime date) {
}
