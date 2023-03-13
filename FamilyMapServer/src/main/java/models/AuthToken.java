package models;


/**
 * This object represents an AuthToken for a user. It has the AuthToken and the associated username
 */
public class AuthToken {
    /**
     * The AuthToken Primary Key
     */
    public String authToken;

    /**
     * The associated username to the AuthToken
     */
    public String username;

    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
