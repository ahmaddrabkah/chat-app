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
            userService.save(new User("ahmad", "12345", "https://www.freepik.com/free-psd/3d-illustration-person-with-sunglasses_27470334.htm#query=user&position=3&from_view=keyword&track=sph"));
            userService.save(new User("mohammad", "00000", "https://www.freepik.com/free-vector/businessman-character-avatar-isolated_6769264.htm#query=user&position=2&from_view=keyword&track=sph"));

        };
    }
}
