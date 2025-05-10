package org.deha.be.dto.user;

import org.deha.be.model.User;

public record ChangePasswordParameters(User user, String password, String newPassword) {
}
