package apiContract;

// import lombok.Getter;
// import lombok.Setter;


/**
 * The response object returned from a login request.
 */
// @Getter
// @Setter
public class LoginResponse {
    /**
     * The newly generated authToken for the request
     */
    public String authtoken;

    /**
     * The username just authenticated
     */
    public String username;

    /**
     * The associated PersonID for the user
     */
    public String personID;

    /**
     * A status indicator of whether the call was successful
     */
    public boolean success;

    public LoginResponse(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }
}
