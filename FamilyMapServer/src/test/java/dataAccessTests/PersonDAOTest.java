package dataAccessTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import dataAccess.PersonDAO;
import exceptions.DataAccessException;
import models.Person;

public class PersonDAOTest {
    private Database db;
    private PersonDAO dao;
    private Person testPerson;

    @BeforeEach
    public void setup() throws DataAccessException {
        db = new Database();
        var conn = db.getConnection();
        dao = new PersonDAO(conn);
        dao.clear();

        testPerson = new Person("1234567", "The Farrnacle", "Cannon", "Farr", "M", "2468", "abcdef", null);
    }

    @AfterEach
    public void clean() {
        db.closeConnection(false);
    }

    @Test
    public void insertSuccess() throws DataAccessException {
        dao.createPerson(testPerson);

        var sameUser = dao.getPersonById(testPerson.personID);

        assertNotNull(sameUser);
        assertEquals(testPerson, sameUser);
    }

    @Test
    public void insertFail() throws DataAccessException {
        dao.createPerson(testPerson);

        assertThrows(DataAccessException.class, () -> dao.createPerson(testPerson));
    }

    @Test
    public void getSuccess() throws DataAccessException {
        dao.createPerson(testPerson);

        var sameUser = dao.getPersonById(testPerson.personID);

        assertNotNull(sameUser);
        assertEquals(testPerson, sameUser);
    }

    @Test
    public void getFail() throws DataAccessException {
        var user = dao.getPersonById("someCoolUsername");
        assertNull(user);
    }

    @Test
    public void clearSuccess() throws DataAccessException {
        dao.createPerson(testPerson);
        dao.clear();
        var sameUser = dao.getPersonById(testPerson.personID);
        assertNull(sameUser);
    }
}
