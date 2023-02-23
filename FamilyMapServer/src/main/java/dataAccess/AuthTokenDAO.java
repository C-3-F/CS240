package dataAccess;

import models.AuthToken;


/**
 * The DAO that interacts with the AuthToken Database. It has methods to Get, Create, and Clear records.
 */
public class AuthTokenDAO {
    /**
     * Returns an authToken Object with an associated user from an AuthToken string
     * 
     * @param authToken The auth token to retrieve
     * @return The authToken object
     */
    public AuthToken getAuthToken(int authToken) {
        return new AuthToken();
    }

    /**
     * Creates a new AuthToken entity in the database
     * 
     * @param authToken The authToken object to insert
     */
    public void create(AuthToken authToken) {

    }

    /**
     * Clears the AuthToken table and any data inside it.
     */
    public void clear() {

    }
}