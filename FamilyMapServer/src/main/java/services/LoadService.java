package services;

import java.util.ArrayList;


import apiContract.LoadRequest;
import apiContract.LoadResponse;
import dataAccess.Database;
import dataAccess.EventDAO;
import dataAccess.PersonDAO;
import dataAccess.UserDAO;
import exceptions.DataAccessException;
import models.Event;
import models.Person;
import models.User;

/**
 * This service is responsible for loading a set of preset data into the
 * database
 */
public class LoadService {

    private Database db;
    private UserDAO userDAO;
    private PersonDAO personDAO;
    private EventDAO eventDAO;
    private boolean success = true;
    private String outMessage = "";

    public LoadService() throws DataAccessException {
        db = new Database();
        userDAO = new UserDAO(db.getConnection());
        personDAO = new PersonDAO(db.getConnection());
        eventDAO = new EventDAO(db.getConnection());
    }

    /**
     * Clears the current state of the database and then loads in new User, Event,
     * and Person data from the given request
     *
     * @param request an object containing lists of the data to populate
     * @return A response object with the status of the call
     */
    public LoadResponse load(LoadRequest request) {
        try {


            var clearService = new ClearService();
            clearService.clear();
            addUsers(request.users);
            addPersons(request.persons);
            addEvents(request.events);
            if (success) {
                outMessage = "Successfully added " + request.users.size() + " users, " + request.persons.size()
                        + " persons, and " + request.events.size() + " events to the database";
            }
            return new LoadResponse(outMessage, success);
        } catch (Exception ex) {
            ex.printStackTrace();
            success = false;
            return new LoadResponse(ex.getMessage(), success);
        } finally {
            db.closeConnection(success);
        }
    }


    private void addUsers(ArrayList<User> users) {
        try {

            for (User user : users) {
                userDAO.createUser(user);
            }
        } catch (DataAccessException ex) {
            success = false;
            outMessage = "Failure loading users";
        }
    }

    private void addPersons(ArrayList<Person> persons) {
        try {

            for (Person person : persons) {
                personDAO.createPerson(person);
            }
        } catch (DataAccessException ex) {
            success = false;
            outMessage = "Failure loading persons";
        }
    }

    private void addEvents(ArrayList<Event> events) {
        try {

            for (Event event : events) {
                eventDAO.createEvent(event);
            }
        } catch (DataAccessException ex) {
            success = false;
            outMessage = "Failure loading events";
        }
    }
}
