package dev.ahmad.authenticationservice.jwt;

import dev.ahmad.authenticationservice.model.LocalUserDetails;
import dev.ahmad.authenticationservice.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConstant jwtConstant;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public JwtTokenAuthenticationFilter(JwtConstant jwtConstant, UserService userService, JwtTokenProvider tokenProvider) {
        this.jwtConstant = jwtConstant;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtConstant.getHeader());

        if (header == null || !header.startsWith(jwtConstant.getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConstant.getPrefix(), "").trim();

        if (tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromJWT(token);

            UsernamePasswordAuthenticationToken auth =
                    userService.findByUsername(username)
                            .map(LocalUserDetails::new)
                            .map(userDetails -> {
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                return authentication;
                            })
                            .orElse(null);

            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
