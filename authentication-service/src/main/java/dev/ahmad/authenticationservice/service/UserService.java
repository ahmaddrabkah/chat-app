package dev.ahmad.authenticationservice.service;

import dev.ahmad.authenticationservice.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    void addRole(String username, String roleName);

    Optional<User> findByUsername(String username);

    boolean exists(String username);

    List<User> findAll();

    String loginUser(Authentication authentication);
}
