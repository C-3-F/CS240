package apiContract;

import java.util.ArrayList;

import models.*;

/**
 * The request object sent when calling the Load endpoint
 */
public class LoadRequest {
    /**
     * A List of users to load into the database
     */
    public ArrayList<User> users;

    /**
     * A list of persons to load into the database
     */
    public ArrayList<Person> persons;

    /**
     * A list of events to load into the databse
     */
    public ArrayList<Event> events;

    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }
}
