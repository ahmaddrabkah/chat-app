package dev.ahmad.authenticationservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.ahmad.authenticationservice.model.LocalUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenProvider {
    private final JwtConstant jwtConstant;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    public JwtTokenProvider(JwtConstant jwtConstant) {
        this.jwtConstant = jwtConstant;
        this.algorithm = Algorithm.HMAC256(jwtConstant.getSecret().getBytes());
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    public String generateToken(Authentication authentication) {
        LocalUserDetails userDetails = (LocalUserDetails) authentication.getPrincipal();
        long now = System.currentTimeMillis();

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(now + jwtConstant.getExpiration() * 1000))
                .withIssuedAt(new Date(now))
                .withClaim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String getUsernameFromJWT(String token) {
        return jwtVerifier.verify(token).getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            jwtVerifier.verify(authToken);
            return true;
        } catch (JWTVerificationException ex) {
            log.error("Invalid JWT token");
        }
        return false;
    }
}
