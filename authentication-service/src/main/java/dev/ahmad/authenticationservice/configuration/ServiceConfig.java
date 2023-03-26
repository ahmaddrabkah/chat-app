package dev.ahmad.authenticationservice.configuration;

import dev.ahmad.authenticationservice.jwt.JwtTokenProvider;
import dev.ahmad.authenticationservice.repository.RoleRepository;
import dev.ahmad.authenticationservice.repository.UserRepository;
import dev.ahmad.authenticationservice.service.LocalUserDetailsService;
import dev.ahmad.authenticationservice.service.UserService;
import dev.ahmad.authenticationservice.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new LocalUserDetailsService(userRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder,
                                   JwtTokenProvider jwtTokenProvider) {
        return new UserServiceImpl(userRepository, roleRepository, passwordEncoder, jwtTokenProvider);
    }
}
