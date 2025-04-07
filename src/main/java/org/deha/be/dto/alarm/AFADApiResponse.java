package org.deha.be.dto.alarm;

import java.time.LocalDateTime;

public record AFADApiResponse(long eventID,
                              String location,
                              float latitude,
                              float longitude,
                              float depth,
                              float magnitude,
                              String province,
                              LocalDateTime date) {
}
