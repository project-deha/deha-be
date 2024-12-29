package me.utku.dehabe.dto.user;

import me.utku.dehabe.model.User;

public record UpdateUserRequestDto(UserDto updateData, User existingUser) {
}
