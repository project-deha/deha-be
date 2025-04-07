package org.deha.be.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionDescription {
    BAD_CREDENTIALS("Failed authentication with given credentials!"),
    ENTITY_NOT_FOUND("Entity not found"),
    EMAIL_SENDING_EXCEPTION("Email could not be sent!"),
    EMAIL_VERIFICATION_EXCEPTION("Email verification failed. Please try again."),
    USER_ALREADY_EXISTS("User already exists with this email."),
    USER_NOT_FOUND("User not found with given email!");

    private final String value;
}
