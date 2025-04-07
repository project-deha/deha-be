package org.deha.be.dto.user;

import org.deha.be.model.User;

public record UpdateUserRequestDto(UserDto updateData, User existingUser) {
}
