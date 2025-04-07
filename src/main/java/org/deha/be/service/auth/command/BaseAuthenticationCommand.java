package org.deha.be.service.auth.command;

import org.deha.be.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseAuthenticationCommand {
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
