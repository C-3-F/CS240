package dataAccessTests;

import dataAccess.Database;
import dataAccess.EventDAO;
import dataAccess.PersonDAO;
import exceptions.DataAccessException;
import models.Event;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest {
    private Database db;
    private EventDAO dao;
    private Event testEvent;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        var conn = db.getConnection();
        dao = new EventDAO(conn);
        dao.clear();

        testEvent = new Event("1234567", "The Farrnacle", "Cannon",(float)123.123, (float)456.456, "United States", "New York", "birth",2000);
    }

    @AfterEach
    public void clean() {
        db.closeConnection(false);
    }

    @Test
    public void insertSuccess() throws DataAccessException {
        dao.createEvent(testEvent);

        var sameEvent = dao.getEventById(testEvent.eventID);

        assertNotNull(sameEvent);
        assertEquals(testEvent, sameEvent);
    }

    @Test
    public void insertFail() throws DataAccessException {
        dao.createEvent(testEvent);

        assertThrows(DataAccessException.class, () -> dao.createEvent(testEvent));
    }

    @Test
    public void getSuccess() throws DataAccessException {
        dao.createEvent(testEvent);

        var sameEvent = dao.getEventById(testEvent.eventID);

        assertNotNull(sameEvent);
        assertEquals(testEvent, sameEvent);
    }

    @Test
    public void getFail() throws DataAccessException {
        var event = dao.getEventById("somerandomeventid");
        assertNull(event);
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        dao.createEvent(testEvent);
        dao.clear();
        var sameEvent = dao.getEventById(testEvent.eventID);
        assertNull(sameEvent);
    }
}
