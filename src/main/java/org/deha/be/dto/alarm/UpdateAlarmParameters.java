package org.deha.be.dto.alarm;

import org.deha.be.model.User;

public record UpdateAlarmParameters(User user, AlarmDto alarmDto) {
}
