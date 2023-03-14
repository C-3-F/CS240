package services;

import apiContract.AllEventsResponse;
import apiContract.EventDetailsResponse;
import dataAccess.Database;
import dataAccess.EventDAO;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;

/**
 * This service interacts with Events and has the functionality to get details
 * of events
 */
public class EventService {
    private EventDAO eventDAO;

    public EventService() throws DataAccessException {
        var db = new Database();
        eventDAO = new EventDAO(db.getConnection());
    }

    /**
     * Gets the details for a single event from a given ID
     * 
     * @param eventID the EventID to get
     * @return the Event Details Object
     */
    public EventDetailsResponse getEventDetails(String eventID) throws DataAccessException, EntityNotFoundException {
        var event = eventDAO.getEventById(eventID);
        if (event == null) {
            throw new EntityNotFoundException("Event ID " + eventID + " not found");
        }
        return new EventDetailsResponse(event.eventID, event.associatedUsername, event.personID, event.latitude,
                event.longitude, event.country, event.city, event.eventType, event.year, true);
    }

    /**
     * Gets a list of all the events from a given personID
     * 
     * @param personID The PersonID to gather events for
     * @return A List of all the events
     */
    public AllEventsResponse getAllEvents(String username) throws DataAccessException {
        var events = eventDAO.getEventsByUsername(username);
        return new AllEventsResponse(events, true);
    }
}
