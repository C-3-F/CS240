package apiContract;

// import lombok.Getter;

// import lombok.Setter;

/**
 * The response object returned when registering a user through the register
 * endpoint
 */
// @Getter
// @Setter
public class RegisterResponse {
    /**
     * The auth token for the newly generated user
     */
    public String authtoken;

    /**
     * The final username of the new user
     */
    public String username;

    /**
     * The associated PersonID for the new user
     */
    public String personID;

    /**
     * A Status indicator of whether the call was successful
     */
    public boolean success;

    public RegisterResponse(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }
}
