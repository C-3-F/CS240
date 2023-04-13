package apiContract;

/**
 * The response object returned when calling the fill endpoint
 */
public class FillResponse {
    /**
     * Status message indiating the result of the call
     */
    public String message;

    /**
     * Status result of whether the call was successful
     */
    public boolean success;

    public FillResponse(String message, boolean success) {
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
