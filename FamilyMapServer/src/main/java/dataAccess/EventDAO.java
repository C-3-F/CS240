package dataAccess;

import models.Event;


/**
 * The DAO that interacts with the Event Database. It has methods to Get, Create, and Clear records.
 */
public class EventDAO {
    /**
     * Returns an event from a given ID
     * 
     * @param eventID the eventID to retrieve
     * @return An event object
     */
    public Event getEventById(int eventID) {
        return new Event();
    }

    /**
     * Creates a new event to insert in the database
     * 
     * @param event the event to insert
     */
    public void createEvent(Event event) {

    }

    /**
     * Clears the Event table and data inside it
     */
    public void clear() {

    }
}
