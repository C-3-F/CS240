package services;

import apiContract.AllEventsResponse;
import apiContract.EventDetailsResponse;

public class EventService {
    /**
     * Gets the details for a single event from a given ID
     * @param eventID the EventID to get
     * @return the Event Details Object
     */
    public EventDetailsResponse getEventDetails(int eventID) {
        return new EventDetailsResponse();
    }


    /**
     * Gets a list of all the events from a given personID
     * @param personID  The PersonID to gather events for
     * @return  A List of all the events
     */
    public AllEventsResponse getAllEvents(int personID) {
        return new AllEventsResponse();
    }
}
