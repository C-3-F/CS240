package apiContract;

// import lombok.Getter;

// import lombok.Setter;

/**
 * The request object sent when doing authentication through the login endpoint
 */
// @Getter
// @Setter
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
