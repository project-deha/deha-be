package me.utku.dehabe.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.utku.dehabe.enums.Role;
import me.utku.dehabe.generic.BaseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
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

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String getUsername() {
        return email;
    }

    public static class UserBuilder {
        public User build() {
            return new User(this.firstName, this.lastName, this.email, this.password);
        }
    }
}
