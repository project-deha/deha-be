package me.utku.dehabe.dto.email;

import org.thymeleaf.context.Context;

public class EmailContextBuilder {
    private final Context context;

    public EmailContextBuilder() {
        this.context = new Context();
    }

    public static EmailContextBuilder builder() {
        return new EmailContextBuilder();
    }

    public EmailContextBuilder setVariable(String key, Object value) {
        context.setVariable(key, value);
        return this;
    }

    public Context build() {
        return context;
    }
}
