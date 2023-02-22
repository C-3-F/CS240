package apiContract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    /**
     * The username to authenticate with
     */
    public String username;

    /**
     * The associated password to authenticate with
     */
    public String password;
}
