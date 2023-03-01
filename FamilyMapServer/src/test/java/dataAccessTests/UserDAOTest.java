package dataAccessTests;

import models.User;
import org.junit.jupiter.api.*;

import dataAccess.Database;
import dataAccess.UserDAO;
import exceptions.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private Database db;
    private UserDAO dao;
    private User testUser;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        var conn = db.getConnection();
        dao = new UserDAO(conn);
        dao.clear();

        testUser = new User("The Farrnacle", "p@ssword!", "c3farr@gmail.com", "Cannon", "Farr", "m", "1234567");
    }

    @AfterEach
    public void clean() {
        db.closeConnection(false);
    }

    @Test
    public void insertSuccess() throws DataAccessException {
        dao.createUser(testUser);

        var sameUser = dao.getUserById(testUser.username);

        assertNotNull(sameUser);
        assertEquals(testUser, sameUser);
    }

    @Test
    public void insertFail() throws DataAccessException {
        dao.createUser(testUser);

        assertThrows(DataAccessException.class, () -> dao.createUser(testUser));
    }

    @Test
    public void getSuccess() throws DataAccessException {
        dao.createUser(testUser);

        var sameUser = dao.getUserById(testUser.username);

        assertNotNull(sameUser);
        assertEquals(testUser, sameUser);
    }

    @Test
    public void getFail() throws DataAccessException {
        var user = dao.getUserById("someCoolUsername");
        assertNull(user);
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        dao.createUser(testUser);
        dao.clear();
        var sameUser = dao.getUserById(testUser.username);
        assertNull(sameUser);
    }
}
