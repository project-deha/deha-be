package org.deha.be.dto.alarm;

import org.deha.be.enums.TurkishCity;

import java.util.List;

public record CreateAlarmDto(float minimumMagnitude, float maximumMagnitude, List<TurkishCity> cities) {
}
