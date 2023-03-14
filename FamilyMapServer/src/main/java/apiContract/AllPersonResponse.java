package apiContract;

import java.util.ArrayList;

import models.Person;


/**
 * The response object returned when retrieving all persons
 */
public class AllPersonResponse {
    /**
     * List of all of the persons returned from the call
     */
    public ArrayList<Person> data;

    /**
     * Status result of whether the call was successful
     */
    public boolean success;

    public AllPersonResponse(ArrayList<Person> data, boolean success) {
        this.data = data;
        this.success = success;
    }
}
