package models;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return authToken.equals(authToken1.authToken) && username.equals(authToken1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, username);
    }
}
