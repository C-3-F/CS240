package services;

import apiContract.AllEventsResponse;
import apiContract.EventDetailsResponse;
import apiContract.EventRequest;
import dataAccess.AuthTokenDAO;
import dataAccess.Database;
import dataAccess.EventDAO;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;
import exceptions.UnauthorizedException;

/**
 * This service interacts with Events and has the functionality to get details
 * of events
 */
public class EventService {
    private EventDAO eventDAO;
    private Database db;
    private AuthTokenDAO authTokenDAO;

    public EventService() throws DataAccessException {
        db = new Database();
        eventDAO = new EventDAO(db.getConnection());
        authTokenDAO = new AuthTokenDAO(db.getConnection());
    }

    /**
     * Gets the details for a single event from a given ID
     * 
     * @param eventID the EventID to get
     * @return the Event Details Object
     */
    public EventDetailsResponse getEventDetails(EventRequest request)
            throws DataAccessException, EntityNotFoundException, UnauthorizedException {
        try {


            var authTokenObj = authTokenDAO.getAuthToken(request.authToken);
            var event = eventDAO.getEventById(request.eventID);
            if (event == null) {
                throw new EntityNotFoundException("The eventID " + request.eventID + " does not exist");
            }
            if (authTokenObj == null || !event.associatedUsername.equals(authTokenObj.username)) {
                throw new UnauthorizedException("Invalid auth token");
            }
            return new EventDetailsResponse(event.eventID, event.associatedUsername, event.personID, event.latitude,
                    event.longitude, event.country, event.city, event.eventType, event.year, true);
        } finally {
            db.closeConnection(true);
        }
    }

    /**
     * Gets a list of all the events from a given personID
     * 
     * @param personID The PersonID to gather events for
     * @return A List of all the events
     */
    public AllEventsResponse getAllEvents(EventRequest request) throws DataAccessException, UnauthorizedException {
        try {
            var authTokenObj = authTokenDAO.getAuthToken(request.authToken);
            if (authTokenObj == null) {
                throw new UnauthorizedException("AuthToken does not exist");
            }
            var events = eventDAO.getEventsByUsername(authTokenObj.username);
            return new AllEventsResponse(events, true);
        } finally {
            db.closeConnection(true);
        }


    }
}
