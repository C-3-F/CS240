package apiContract;

import java.util.List;

import models.Person;


/**
 * The response object returned when retrieving all persons
 */
public class AllPersonResponse {
    /**
     * List of all of the persons returned from the call
     */
    public List<Person> data;

    /**
     * Status result of whether the call was successful
     */
    public boolean success;

    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
