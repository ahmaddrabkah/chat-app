package dev.ahmad.authenticationservice.controller;

import dev.ahmad.authenticationservice.controller.request.LoginRequest;
import dev.ahmad.authenticationservice.controller.response.JwtAuthenticationResponse;
import dev.ahmad.authenticationservice.controller.response.UserSummaryResponse;
import dev.ahmad.authenticationservice.exception.UserNotFoundException;
import dev.ahmad.authenticationservice.model.LocalUserDetails;
import dev.ahmad.authenticationservice.model.User;
import dev.ahmad.authenticationservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signin")
    public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("sign in for user {}", loginRequest.getUsername());
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        String token = userService.loginUser(authenticate);
        return new JwtAuthenticationResponse(token);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findUser(@PathVariable("username") String username) {

        log.info("fetching user {}", username);
        return userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findAll() {

        log.info("fetching all users");
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSummaryResponse getCurrentUser(@AuthenticationPrincipal LocalUserDetails userDetails) {
        return new UserSummaryResponse(userDetails.getId(), userDetails.getName(), userDetails.getUsername(), userDetails.getProfilePictureUrl());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserSummaryResponse> findAllUserSummaries(@AuthenticationPrincipal LocalUserDetails userDetails) {

        log.info("fetching all users summaries");
        return userService.findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(userDetails.getUsername()))
                .map(this::generateUserSummery)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/summary/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSummaryResponse getUserSummary(@PathVariable("username") String username) {
        log.info("fetching user {}", username);
        return userService
                .findByUsername(username)
                .map(this::generateUserSummery)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    private UserSummaryResponse generateUserSummery(User user) {
        return new UserSummaryResponse(user.getId(), user.getName(), user.getUsername(), user.getProfilePictureUrl());
    }


}
