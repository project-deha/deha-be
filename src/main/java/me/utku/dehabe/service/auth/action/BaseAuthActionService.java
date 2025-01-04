package me.utku.dehabe.service.auth.action;

import me.utku.dehabe.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseAuthActionService {
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
