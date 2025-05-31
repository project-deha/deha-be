package org.deha.be.dto.user;

import org.deha.be.model.User;

public record ResetPasswordEmailParameters(User user, String newPassword) {
}
