package org.deha.be.dto.user;

public record RegisterRequestDto(String email, String firstName, String lastName, String password) {
}
