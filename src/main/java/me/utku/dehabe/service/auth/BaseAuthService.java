package me.utku.dehabe.service.auth;

import me.utku.dehabe.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseAuthService {
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
