package com.econocom.login.repository;

import com.econocom.login.model.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Simple in-memory user repository with hardcoded users.
 */
@Repository
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    /**
     * Initialize the in-memory users.
     */
    @PostConstruct
    public void init() {
        users.put("user@econocom.com", new User(1L, "user@econocom.com", "password123", Collections.singletonList("ROLE_USER")));
        users.put("admin@econocom.com", new User(2L, "admin@econocom.com", "admin123", Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
    }

    /**
     * Find a user by email.
     *
     * @param email email address
     * @return optional user
     */
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }
}
