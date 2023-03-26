package dev.ahmad.authenticationservice.controller.response;

public class UserSummaryResponse {

    private Long id;
    private String name;
    private String username;
    private String profilePicture;

    public UserSummaryResponse() {
    }

    public UserSummaryResponse(Long id, String name, String username, String profilePicture) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.profilePicture = profilePicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}