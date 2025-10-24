package com.example_jelle.backenddico.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This utility class provides methods for handling JSON Web Tokens (JWTs).
 * It is responsible for generating, parsing, and validating tokens.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private static final long EIGHT_HOURS_IN_MILLIS = 8 * 60 * 60 * 1000;

    /**
     * Creates the signing key for the JWT from the Base64 encoded secret key.
     * @return The signing key.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username (subject) from a given JWT.
     * @param token The JWT.
     * @return The username.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from a given JWT.
     * @param token The JWT.
     * @return The expiration date.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts the 'scope' claim (authorities) from a given JWT.
     * @param token The JWT.
     * @return A list of strings representing the scope.
     */
    @SuppressWarnings("unchecked")
    public List<String> extractScope(String token) {
        final Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("scope");
    }

    /**
     * Extracts the 'roles' claim (authorities) from a given JWT.
     * @param token The JWT.
     * @return A list of strings representing the roles.
     */
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        final Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("roles");
    }

    /**
     * A generic method to extract a specific claim from a JWT using a claims resolver function.
     * @param token The JWT.
     * @param claimsResolver A function that takes the claims and returns the desired value.
     * @param <T> The type of the claim to be extracted.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses the JWT and returns all its claims.
     * @param token The JWT to parse.
     * @return The claims object.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if a JWT has expired.
     * @param token The JWT.
     * @return true if the token is expired, false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a standard JWT for an authenticated user.
     * @param userDetails The details of the user for whom the token is generated.
     * @return The generated JWT string.
     */
    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles) // Add roles claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a JWT with a specific username, scope (authorities), and expiration time.
     * @param username The username for the token subject.
     * @param authorities The list of authorities to include in the 'scope' claim.
     * @param expirationMillis The expiration time in milliseconds.
     * @return The generated JWT string.
     */
    public String generateToken(String username, List<String> authorities, long expirationMillis) {
        return Jwts.builder()
                .setSubject(username)
                .claim("scope", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a delegated token for a specific user (patient) with limited scope and expiration.
     * @param username The username of the patient for whom the token is delegated.
     * @return A JWT with 'read:dashboard' scope, valid for 8 hours.
     */
    public String generateDelegatedToken(String username) {
        List<String> scope = List.of("read:dashboard");
        return generateToken(username, scope, EIGHT_HOURS_IN_MILLIS);
    }

    /**
     * Validates a JWT against the user details.
     * It checks if the username in the token matches the one in UserDetails and if the token has not expired.
     * @param token The JWT to validate.
     * @param userDetails The user details to validate against.
     * @return true if the token is valid, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
