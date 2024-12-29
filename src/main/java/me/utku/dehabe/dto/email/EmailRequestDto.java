package me.utku.dehabe.dto.email;

import org.thymeleaf.context.Context;

public record EmailRequestDto(String to, String subject, String templateName, Context context) {
}
