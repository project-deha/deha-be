package org.deha.be.dto.alarm;

import org.deha.be.enums.TurkishCity;

public record AlarmFilterDto(
        float magnitude,
        TurkishCity city
) {
}
