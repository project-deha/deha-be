package me.utku.dehabe.dto.emailverification;

import me.utku.dehabe.dto.user.UserDto;
import me.utku.dehabe.model.EmailVerification;

import java.io.Serializable;

/**
 * DTO for {@link EmailVerification}
 */
public record EmailVerificationDto(UserDto user, int code) implements Serializable {
}