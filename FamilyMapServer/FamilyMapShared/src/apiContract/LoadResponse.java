package apiContract;

// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter

/**
 * The response object returned when calling the load endpoint
 */
public class LoadResponse {
    /**
     * A message indicating the result of the call
     */
    public String message;

    /**
     * A status result indicating whether the call was successful
     */
    public boolean success;

    public LoadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
