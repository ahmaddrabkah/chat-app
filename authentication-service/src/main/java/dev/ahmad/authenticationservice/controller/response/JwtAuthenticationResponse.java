package dev.ahmad.authenticationservice.controller.response;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class JwtAuthenticationResponse {
    @NonNull
    private String accessToken;
    private String tokenType = "Bearer";


    public JwtAuthenticationResponse(@NonNull String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
