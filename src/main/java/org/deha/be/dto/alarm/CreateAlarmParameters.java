package org.deha.be.dto.alarm;

import org.deha.be.model.User;

public record CreateAlarmParameters(User user, CreateAlarmDto createAlarmDto) {
}
