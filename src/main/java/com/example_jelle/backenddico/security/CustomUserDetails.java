// Custom implementation of Spring Security's UserDetails interface.
package com.example_jelle.backenddico.security;

import com.example_jelle.backenddico.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    // Constructs a new CustomUserDetails.
    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    // Gets the underlying User object.
    public User getUser() {
        return user;
    }

    // Gets the user's ID.
    public Long getId() {
        return user.getId();
    }

    // Gets the authorities granted to the user.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Gets the user's password.
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Gets the username (email) for authentication.
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // Indicates whether the user's account has expired.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indicates whether the user is locked or unlocked.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Indicates whether the user's credentials (password) has expired.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indicates whether the user is enabled or disabled.
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
