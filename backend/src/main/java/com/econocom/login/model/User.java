package com.econocom.login.model;

import java.util.List;

/**
 * Simple in-memory user entity.
 */
public class User {

    private Long id;
    private String email;
    private String password;
    private List<String> roles;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Construct a user.
     *
     * @param id       
     * @param email    
     * @param password 
     * @param roles    
     */
    public User(Long id, String email, String password, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
