package com.example_jelle.backenddico.security;

import com.example_jelle.backenddico.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is a filter that intercepts every incoming request once.
 * It is responsible for extracting the JWT from the Authorization header, validating it,
 * and setting the user's authentication in the Spring Security context if the token is valid.
 * IMPORTANT: This class should NOT be a @Component. It is manually instantiated in SecurityConfig.
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * The main filter logic. It checks for a "Bearer " token in the Authorization header.
     * If a token is found, it extracts the username and validates the token.
     * If validation is successful, it creates an authentication token and sets it in the SecurityContextHolder.
     * @param request The incoming HTTP request.
     * @param response The HTTP response.
     * @param chain The filter chain.
     * @throws IOException if an I/O error occurs.
     * @throws ServletException if a servlet error occurs.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (JwtException e) {
                logger.warn("Invalid JWT token received: {}. Token: {}", e.getMessage(), jwt);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Authenticated user '{}', setting security context", username);
            }
        }

        chain.doFilter(request, response);
    }
}
