package org.deha.be.dto.user;

import org.deha.be.model.User;

public record UpdateUserRequestParameters(UserDto updateData, User existingUser) {
}
