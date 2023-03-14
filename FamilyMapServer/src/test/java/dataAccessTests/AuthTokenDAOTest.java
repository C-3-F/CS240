package dataAccessTests;

import dataAccess.AuthTokenDAO;
import dataAccess.Database;
import exceptions.DataAccessException;
import models.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDAOTest {
    private Database db;
    private AuthTokenDAO dao;
    private AuthToken authToken;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        var conn = db.getConnection();
        dao = new AuthTokenDAO(conn);
        dao.clear();

        authToken = new AuthToken("1234567", "The Farrnacle");
    }

    @AfterEach
    public void clean() {
        db.closeConnection(false);
    }

    @Test
    public void insertSuccess() throws DataAccessException {
        dao.create(authToken);

        var sameToken = dao.getAuthToken(authToken.authToken);

        assertNotNull(sameToken);
        assertEquals(authToken, sameToken);
    }

    @Test
    public void insertFail() throws DataAccessException {
        dao.create(authToken);

        assertThrows(DataAccessException.class, () -> dao.create(authToken));
    }

    @Test
    public void getSuccess() throws DataAccessException {
        dao.create(authToken);

        var sameToken = dao.getAuthToken(authToken.authToken);

        assertNotNull(sameToken);
        assertEquals(authToken, sameToken);
    }

    @Test
    public void getFail() throws DataAccessException {
        var event = dao.getAuthToken("somerandomeventid");
        assertNull(event);
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        dao.create(authToken);
        dao.clear();
        var sameEvent = dao.getAuthToken(authToken.authToken);
        assertNull(sameEvent);
    }
}
