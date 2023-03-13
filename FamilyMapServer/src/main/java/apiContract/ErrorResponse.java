package apiContract;

public class ErrorResponse {
    public boolean success;
    public String message;
    public ErrorResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
