package org.deha.be.dto.user;

public record RegisterRequestParameters(String email, String firstName, String lastName, String password) {
}
