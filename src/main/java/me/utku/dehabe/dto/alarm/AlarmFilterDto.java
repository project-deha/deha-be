package me.utku.dehabe.dto.alarm;

import me.utku.dehabe.enums.TurkishCity;

public record AlarmFilterDto(
        float magnitude,
        TurkishCity city
) {
}
