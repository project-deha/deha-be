package me.utku.dehabe.dto.user;

import me.utku.dehabe.enums.Role;

import java.util.Set;
import java.util.UUID;

public record UserDto(UUID id, String email, String firstName, String lastName, Set<Role> authorities,
                      boolean isVerified) {
}
