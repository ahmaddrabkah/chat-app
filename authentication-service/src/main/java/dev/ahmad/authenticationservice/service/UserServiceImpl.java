package dev.ahmad.authenticationservice.service;

import dev.ahmad.authenticationservice.jwt.JwtTokenProvider;
import dev.ahmad.authenticationservice.model.Role;
import dev.ahmad.authenticationservice.model.User;
import dev.ahmad.authenticationservice.repository.RoleRepository;
import dev.ahmad.authenticationservice.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User save(User user) {
        if (exists(user.getUsername()))
            return findByUsername(user.getUsername()).get();
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void addRole(String username, String roleName) {
        findByUsername(username).map(user -> {
                    Role role = roleRepository.findByName(roleName)
                            .orElseGet(() -> roleRepository.save(new Role(roleName)));
                    user.getRoles().add(role);
                    return userRepository.save(user);
                }
        );
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean exists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public String loginUser(Authentication authentication) {
        return jwtTokenProvider.generateToken(authentication);
    }
}