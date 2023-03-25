package dev.ahmad.authenticationservice.configuration;

import dev.ahmad.authenticationservice.jwt.JwtConstant;
import dev.ahmad.authenticationservice.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public JwtTokenProvider jwtTokenProvider(JwtConstant jwtConstant) {
        return new JwtTokenProvider(jwtConstant);
    }

    @Bean
    public JwtConstant jetConstants(@Value("${security.jwt.uri}") String uri,
                                    @Value("${security.jwt.header}") String header,
                                    @Value("${security.jwt.prefix}") String prefix,
                                    @Value("${security.jwt.expiration}") int expiration,
                                    @Value("${security.jwt.secret}") String secret) {
        return new JwtConstant(uri, header, prefix, expiration, secret);
    }
}
