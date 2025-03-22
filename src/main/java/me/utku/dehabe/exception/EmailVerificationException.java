package me.utku.dehabe.exception;

import me.utku.dehabe.enums.ExceptionDescription;

public class EmailVerificationException extends RuntimeException {
    public EmailVerificationException() {
        super(ExceptionDescription.EMAIL_VERIFICATION_EXCEPTION.getValue());
    }
}
