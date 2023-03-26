package dev.ahmad.authenticationservice.service;

import dev.ahmad.authenticationservice.model.LocalUserDetails;
import dev.ahmad.authenticationservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LocalUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public LocalUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(LocalUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user with username: %s not found", username)));
    }
}
