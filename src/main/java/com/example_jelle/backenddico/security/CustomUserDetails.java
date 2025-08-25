package com.example_jelle.backenddico.security;

import com.example_jelle.backenddico.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * This class is a custom implementation of Spring Security's UserDetails interface.
 * It wraps the application's own User entity, allowing the full User object to be stored
 * in the security principal. This provides convenient access to user details throughout the application.
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Provides access to the full, underlying User entity.
     * @return The wrapped User object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the authorities granted to the user.
     * It converts the application's Role enum into a Spring Security GrantedAuthority.
     * The role must be prefixed with "ROLE_" for hasRole() checks to work correctly.
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    /**
     * Returns the user's password.
     * @return The hashed password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     * In this application, the user's email is used as their username.
     * @return The user's email address.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     * @return true if the user's account is valid (non-expired), false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     * @return true if the user is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     * @return true if the user's credentials are valid (non-expired), false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
