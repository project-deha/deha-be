package org.deha.be.exception;

import org.deha.be.enums.ExceptionDescription;

public class OperationDeniedException extends RuntimeException {
    public OperationDeniedException(ExceptionDescription description) {
        super(description.getValue());
    }
}
