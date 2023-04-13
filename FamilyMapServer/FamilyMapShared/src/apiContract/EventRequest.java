package apiContract;

public class EventRequest {
    public String authToken;
    public String eventID;
    public EventRequest(String authToken, String eventID) {
        this.authToken = authToken;
        this.eventID = eventID;
    }
}
