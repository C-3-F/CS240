package apiContract;

public class PersonRequest {
    public String authToken;
    public String personID;

    public PersonRequest(String authToken, String personID) {
        this.authToken = authToken;
        this.personID = personID;
    }
}
