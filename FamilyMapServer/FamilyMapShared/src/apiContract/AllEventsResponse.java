package apiContract;

import java.util.ArrayList;

import models.Event;

/**
 * The response object returned when retrieving all events
 */
public class AllEventsResponse {
    /**
     * A list of all the Events that get returned
     */
    public ArrayList<Event> data;

    /**
     * Status result of whether the call was successful
     */
    public boolean success;

    public AllEventsResponse(ArrayList<Event> data, boolean success) {
        this.data = data;
        this.success = success;
    }

}
