package services;

import apiContract.AllPersonResponse;
import apiContract.PersonDetailsResponse;
import dataAccess.Database;
import dataAccess.PersonDAO;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;

/**
 * This service is responsible for interacting with people data and retrieving
 * person information from the database.
 */
public class PersonService {
    private PersonDAO personDAO;

    public PersonService() throws DataAccessException {
        var db = new Database();
        personDAO = new PersonDAO(db.getConnection());
    }

    /**
     * Gets the details for a single person from a given personID
     * 
     * @param personID The personID to get details for
     * @return a response object containing the details for the given personID
     */
    public PersonDetailsResponse getPersonDetails(String personID) throws DataAccessException, EntityNotFoundException {
        var person = personDAO.getPersonById(personID);
        if (person == null) {
            throw new EntityNotFoundException("The personID " + personID + " does not exist in the database");
        }
        return new PersonDetailsResponse(person.personID, person.associatedUsername, person.firstName, person.lastName,
                person.gender, person.fatherID, person.motherID, person.spouseID, true);
    }

    /**
     * Returns a list of all the persons corresponding to a person's family
     * 
     * @param personID the personID of the user to get all family members
     * @return a response object containing a list of the person's family members
     */
    public AllPersonResponse getAllPersons(String username) throws DataAccessException {
        var persons = personDAO.getPersonsByUsername(username);
        return new AllPersonResponse(persons, true);
    }
}
