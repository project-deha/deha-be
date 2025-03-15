package me.utku.dehabe.exception;

import me.utku.dehabe.enums.ExceptionDescription;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException() {
        super(ExceptionDescription.EMAIL_SENDING_EXCEPTION.getValue());
    }
}
