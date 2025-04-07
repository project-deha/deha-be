package org.deha.be.dto.emailverification;

import org.deha.be.dto.user.UserDto;
import org.deha.be.model.EmailVerification;

import java.io.Serializable;

/**
 * DTO for {@link EmailVerification}
 */
public record EmailVerificationDto(UserDto user, int code) implements Serializable {
}