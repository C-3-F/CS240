package apiContract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    /**
     * The newly generated authToken for the request
     */
    public String authToken;

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
}
