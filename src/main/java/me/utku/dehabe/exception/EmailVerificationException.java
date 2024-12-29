package me.utku.dehabe.exception;

public class EmailVerificationException extends RuntimeException {
    public EmailVerificationException() {
        super(ExceptionDescription.EMAIL_VERIFICATION_EXCEPTION.getValue());
    }
}
