package apiContract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    /**
     * The auth token for the newly generated user
     */
    public String authToken;

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
}
