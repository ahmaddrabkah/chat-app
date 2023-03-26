package dev.ahmad.authenticationservice.jwt;

public class JwtConstant {
    private final String Uri;
    private final String header;
    private final String prefix;
    private final int expiration;
    private final String secret;

    public JwtConstant(String uri, String header, String prefix, int expiration, String secret) {
        Uri = uri;
        this.header = header;
        this.prefix = prefix;
        this.expiration = expiration;
        this.secret = secret;
    }

    public String getUri() {
        return Uri;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getSecret() {
        return secret;
    }
}
