package org.deha.be.enums;

import java.time.ZoneId;

public enum TimeZone {
    TURKEY("Europe/Istanbul"),
    UTC("UTC");

    private final String zone;

    TimeZone(String zone) {
        this.zone = zone;
    }

    public ZoneId getZoneId() {
        return ZoneId.of(zone);
    }
}
