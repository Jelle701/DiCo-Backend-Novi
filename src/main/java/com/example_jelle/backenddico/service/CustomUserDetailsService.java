package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.Role;
import com.example_jelle.backenddico.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowerCaseUsername = username.toLowerCase();
        User user = userService.findByUsername(lowerCaseUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        Role userRole = user.getRole();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.name()));

        // If the user is an admin, grant all other roles as well
        if (userRole == Role.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.PATIENT.name()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.GUARDIAN.name()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.PROVIDER.name()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }
}
