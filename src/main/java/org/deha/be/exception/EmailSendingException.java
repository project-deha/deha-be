package org.deha.be.exception;

import org.deha.be.enums.ExceptionDescription;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException() {
        super(ExceptionDescription.EMAIL_SENDING_EXCEPTION.getValue());
    }
}
