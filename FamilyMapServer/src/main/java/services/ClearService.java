package services;

import apiContract.ClearResponse;
import dataAccess.AuthTokenDAO;
import dataAccess.Database;
import dataAccess.EventDAO;
import dataAccess.PersonDAO;
import dataAccess.UserDAO;
import exceptions.DataAccessException;

/**
 * This service is responsible for clearing the database
 */
public class ClearService {

    private Database db;
    private UserDAO userDao;
    private PersonDAO personDAO;
    private AuthTokenDAO authTokenDAO;
    private EventDAO eventDAO;
    private boolean success = true;
    private String outMessage = "";

    public ClearService() throws DataAccessException {
        db = new Database();
        userDao = new UserDAO(db.getConnection());
        personDAO = new PersonDAO(db.getConnection());
        authTokenDAO = new AuthTokenDAO(db.getConnection());
        eventDAO = new EventDAO(db.getConnection());
    }

    /**
     * Clears the data in all of the database tables (User, Person, Event,
     * AuthToken)
     * 
     * @return Returns a ClearResponse object that indicates the result of the clear
     */
    public ClearResponse clear() {
        try {

            userDao.clear();
            personDAO.clear();
            authTokenDAO.clear();
            eventDAO.clear();
            success = true;
            outMessage = "Clear succeeded";
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            success = false;
            outMessage = "Error: " + ex.getMessage();
        } finally {
            db.closeConnection(success);
        }

        return new ClearResponse(outMessage, success);
    }
}
