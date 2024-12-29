package me.utku.dehabe.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionDescription {
    USER_NOT_FOUND("User not found with given email!"),
    BAD_CREDENTIALS("Bad credentials: Failed authentication with given credentials!"),
    EMAIL_SENDING_EXCEPTION("Email could not be sent!"),
    EMAIL_VERIFICATION_EXCEPTION("Email verification failed. Please try again.");

    private final String value;
}
