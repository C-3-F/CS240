package apiContract;

/**
 * The request object sent when calling the fill endpoint
 */
public class FillRequest {
    /**
     * Username of user to fill
     */
    public String username;

    /**
     * integer representing how many generations to fill
     */
    public int generations;

    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
