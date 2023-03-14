package serviceTests;

import apiContract.FillRequest;
import dataAccess.Database;
import dataAccess.EventDAO;
import dataAccess.PersonDAO;
import dataAccess.UserDAO;
import exceptions.DataAccessException;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.FillService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private FillService service;
    private UserDAO userDAO;
    private Database db;
    private User user;

    @BeforeEach
    public void setup() throws DataAccessException, IOException
    {
        service = new FillService();
        db = new Database();
        userDAO = new UserDAO(db.getConnection());
        user = new User("user","password","test@test.com","cannon","farr","m",null);
        userDAO.createUser(user);
        db.closeConnection(true);
    }

    @Test
    public void FillSuccess()
    {
        try {
            var request = new FillRequest(user.username, 4);
            var response = service.fill(request);
            var personDAO = new PersonDAO(db.getConnection());
            var eventDAO = new EventDAO(db.getConnection());
            var events = eventDAO.getEventsByUsername(user.username);
            var persons = personDAO.getPersonsByUsername(user.username);
            assertTrue(response.success);
            assertEquals(31,persons.size());
            assertEquals(91,events.size());
        } catch (DataAccessException ex)
        {
            fail(ex.getMessage());
        } finally {
            db.closeConnection(false);
        }
    }

    @Test
    public void FillUsernameDoesNotExist() {
        var request = new FillRequest("bogusUsername",4);
        var response = service.fill(request);
        assertFalse(response.success);
        assertEquals("Username doesn't exist",response.message);
    }

    @AfterEach
    public void clear() throws DataAccessException
    {
        var clearService = new ClearService();
        clearService.clear();
    }

}
