package org.deha.be.dto.user;

import org.deha.be.enums.Role;

import java.util.Set;
import java.util.UUID;

public record UserDto(UUID id,
                      String email,
                      String firstName,
                      String lastName,
                      Set<Role> authorities,
                      boolean isVerified) {
}
