package org.deha.be.dto.email;

import org.thymeleaf.context.Context;

public record EmailRequestDto(String to, String subject, String templateName, Context context) {
}
