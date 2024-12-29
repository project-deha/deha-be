package me.utku.dehabe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_UNVALIDATED_RESEARCHER("UNVALIDATED_RESEARCHER"),
    ROLE_RESEARCHER("RESEARCHER");

    private final String value;

    @Override
    public String getAuthority() {
        return name();
    }
}
