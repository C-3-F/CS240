package apiContract;


/**
 * The response object returned when calling the Clear endpoint
 */
public class ClearResponse {
    /**
     * Message indicating the result of the clear
     */
    public String message;

    /**
     * Status result indicating whether the call was successful
     */
    public boolean success;

    public ClearResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
