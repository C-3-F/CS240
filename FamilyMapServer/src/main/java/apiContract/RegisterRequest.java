package apiContract;

// import lombok.Getter;

// import lombok.Setter;

/**
 * The request object sent when registering a new user through the register
 * endpoint
 */
// @Getter
// @Setter
public class RegisterRequest {
    /**
     * The username to be registered
     */
    public String username;

    /**
     * The password of the user
     */
    public String password;

    /**
     * The email of the user
     */
    public String email;

    /**
     * The first name of the user
     */
    public String firstName;

    /**
     * The last name of the user
     */
    public String lastName;

    /**
     * The gender of the User "m" of "f"
     */
    public String gender;
}
