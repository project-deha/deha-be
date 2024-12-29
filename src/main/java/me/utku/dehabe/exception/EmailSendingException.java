package me.utku.dehabe.exception;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(Throwable cause) {
        super(ExceptionDescription.EMAIL_SENDING_EXCEPTION.getValue(), cause);
    }
}
