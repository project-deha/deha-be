package org.deha.be.dto.alarm;

import org.deha.be.enums.TurkishCity;

public record CreateAlarmDto(float minimumMagnitude, float maximumMagnitude, TurkishCity city) {
}
