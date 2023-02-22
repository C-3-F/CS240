package apiContract;

import java.util.List;

import models.*;

public class LoadRequest {
    /**
     * A List of users to load into the database
     */
    public List<User> users;

    /**
     * A list of persons to load into the database
     */
    public List<Person> persons;

    /**
     * A list of events to load into the databse
     */
    public List<Event> events;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
