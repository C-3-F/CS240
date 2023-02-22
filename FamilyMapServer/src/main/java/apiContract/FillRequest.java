package apiContract;

public class FillRequest {
    /**
     * Username of user to fill
     */
    public String username;

    /**
     * integer representing how many generations to fill
     */
    public int generations;

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
