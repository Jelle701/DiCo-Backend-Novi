package com.example_jelle.backenddico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This entity represents an authority (or role) granted to a user.
 * It is part of the standard Spring Security database schema for storing user roles.
 * NOTE: The use of two @Id annotations is not standard JPA and will likely cause issues.
 * A composite key should be defined using @IdClass or @EmbeddedId.
 */
@Entity
@Table(name = "authorities")
public class Authority {

    /**
     * The username of the user to whom the authority is granted. Part of the composite primary key.
     */
    @Id
    @Column(nullable = false)
    private String username;

    /**
     * The name of the authority (e.g., 'ROLE_USER', 'ROLE_ADMIN'). Part of the composite primary key.
     */
    @Id
    @Column(nullable = false)
    private String authority;

    public Authority() {}

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
