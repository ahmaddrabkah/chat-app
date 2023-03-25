package dev.ahmad.authenticationservice.service;

import dev.ahmad.authenticationservice.jwt.JwtTokenProvider;
import dev.ahmad.authenticationservice.model.User;
import dev.ahmad.authenticationservice.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User save(User user) {
        if (exists(user.getUsername()))
            return findByUsername(user.getUsername()).get();
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean exists(String username) {
        return userRepository.existsByUsername(username);
    }

    public List<User> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public String loginUser(Authentication authentication) {
        return jwtTokenProvider.generateToken(authentication);
    }
}