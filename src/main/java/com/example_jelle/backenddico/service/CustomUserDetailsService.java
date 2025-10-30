// Custom implementation of Spring Security's UserDetailsService.
package com.example_jelle.backenddico.service;

import com.example_jelle.backenddico.model.enums.Role;
import com.example_jelle.backenddico.model.User;
import com.example_jelle.backenddico.repository.UserRepository;
import com.example_jelle.backenddico.security.CustomUserDetails;
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

    private final UserRepository userRepository;

    // Constructs a new CustomUserDetailsService.
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Loads user-specific data by username (email).
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String lowerCaseEmail = email.toLowerCase();
        User user = userRepository.findByEmail(lowerCaseEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<GrantedAuthority> authorities = new ArrayList<>();
        Role userRole = user.getRole();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.name()));

        // If the user is an admin, grant all other roles as well
        if (userRole == Role.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.PATIENT.name()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.GUARDIAN.name()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + Role.PROVIDER.name()));
        }

        // Return our custom UserDetails object that includes the user ID
        return new CustomUserDetails(user, authorities);
    }
}
