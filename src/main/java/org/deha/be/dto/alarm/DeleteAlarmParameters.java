package org.deha.be.dto.alarm;

import org.deha.be.model.User;

import java.util.UUID;

public record DeleteAlarmParameters(User user, UUID alarmId) {
}
