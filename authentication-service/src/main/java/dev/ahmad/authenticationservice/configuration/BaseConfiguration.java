package dev.ahmad.authenticationservice.configuration;

import dev.ahmad.authenticationservice.model.User;
import dev.ahmad.authenticationservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BaseConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(UserService userService) {
        return args -> {
            userService.save(new User("Ahmad Drabkah", "ahmaddrabkah", "12345", "https://bobbyhadz.com/images/blog/react-prevent-multiple-button-clicks/thumbnail.webp"));
            userService.save(new User("Mohammad Ali", "mohammad", "00000", "https://images.pexels.com/photos/20787/pexels-photo.jpg?auto=compress&cs=tinysrgb&h=350"));

        };
    }
}
