package apiContract;

public class FillResponse {
    /**
     * Status message indiating the result of the call
     */
    public String message;

    /**
     * Status result of whether the call was successful
     */
    public boolean success;

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
