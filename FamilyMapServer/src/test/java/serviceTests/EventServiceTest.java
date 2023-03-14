package serviceTests;

import apiContract.EventRequest;
import dataAccess.AuthTokenDAO;
import dataAccess.Database;
import dataAccess.EventDAO;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;
import exceptions.UnauthorizedException;
import models.AuthToken;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.EventService;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {

    private EventService service;
    private EventDAO eventDAO;
    private AuthTokenDAO authTokenDAO;
    private Database db;
    private AuthToken token;
    private Event event1;
    private Event event2;

    @BeforeEach
    public void setup() throws DataAccessException
    {
        service = new EventService();
        db = new Database();
        eventDAO = new EventDAO(db.getConnection());
        authTokenDAO = new AuthTokenDAO(db.getConnection());
        token = new AuthToken("12-34-56","farrnacle");
        authTokenDAO.create(token);

        event1 = new Event("12345","farrnacle","123456",(float)123.123,(float)123.123,"United States","Chicago","birth", 2000);
        event2 = new Event("45678","farrnacle","123456",(float)456.456,(float)456.456,"United States","Salt Lake City","death", 2060);
        eventDAO.createEvent(event1);
        eventDAO.createEvent(event2);
        db.closeConnection(true);
    }

    @Test
    public void RetrieveEventByIDSuccess()
    {
        try {
            var request = new EventRequest(token.authToken, "12345");
            var response = service.getEventDetails(request);
            var newEvent = new Event(response.eventID,response.associatedUsername,response.personID,response.latitude,response.longitude,response.country,response.city, response.eventType,response.year);
            assertEquals(event1,newEvent);
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void RetreiveAllEventsSuccess()
    {
        try {
            var request = new EventRequest(token.authToken,"");
            var response = service.getAllEvents(request);
            assertEquals(2,response.data.size());
            var newEvent1 = response.data.get(0);
            var newEvent2 = response.data.get(1);
            var newEvent1Obj = new Event(newEvent1.eventID,newEvent1.associatedUsername,newEvent1.personID,newEvent1.latitude,newEvent1.longitude,newEvent1.country,newEvent1.city, newEvent1.eventType,newEvent1.year);
            var newEvent2Obj = new Event(newEvent2.eventID,newEvent2.associatedUsername,newEvent2.personID,newEvent2.latitude,newEvent2.longitude,newEvent2.country,newEvent2.city, newEvent2.eventType,newEvent2.year);
            assertEquals(event1,newEvent1Obj);
            assertEquals(event2,newEvent2Obj);

        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getAllEventsFail()
    {
        var request = new EventRequest("bogusauthtoken","");
        assertThrows(UnauthorizedException.class, () -> service.getAllEvents(request));
    }

    @Test
    public void getEventFail()
    {
        var request = new EventRequest("bogusauthtoken","12345");
        assertThrows(UnauthorizedException.class, () -> service.getEventDetails(request));
    }

    @Test
    public void getEventDoesNotExist()
    {
        var request = new EventRequest(token.authToken, "000001");
        assertThrows(EntityNotFoundException.class, () -> service.getEventDetails(request));
    }

    @AfterEach
    public void clear() throws DataAccessException
    {
        var clearService = new ClearService();
        clearService.clear();
    }
}
