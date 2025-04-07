package org.deha.be.exception;

import org.deha.be.enums.ExceptionDescription;

public class EmailVerificationException extends RuntimeException {
    public EmailVerificationException() {
        super(ExceptionDescription.EMAIL_VERIFICATION_EXCEPTION.getValue());
    }
}
