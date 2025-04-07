package org.deha.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.deha.be.enums.Role;
import org.deha.be.generic.BaseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> authorities = Set.of(Role.ROLE_UNVALIDATED_RESEARCHER);

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private boolean isVerified = false;

    @Override
    public String getUsername() {
        return email;
    }
}
